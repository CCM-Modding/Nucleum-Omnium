package ccm.nucleum_omnium.client.model.tcn;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;

/**
 * Techne model importer, based on iChun's Hats importer
 */
@SideOnly(Side.CLIENT)
public class TechneModel extends ModelBase implements IModelCustom
{

    public static final List<String>         cubeTypes      = Arrays.asList("d9e621f7-957f-4b77-b1ae-20dcd0da7751", "de81aa14-bd60-4228-8d8d-5238bcd3caaa");

    private final String                     fileName;

    private final Map<String, byte[]>        zipContents    = new HashMap<String, byte[]>();

    private final Map<String, ModelRenderer> parts          = new LinkedHashMap<String, ModelRenderer>();

    private String                           texture        = null;

    private int                              textureName;

    private boolean                          textureNameSet = false;

    public TechneModel(final String fileName,
                       final URL resource) throws ModelFormatException
    {
        this.fileName = fileName;
        this.loadTechneModel(resource);
    }

    private void loadTechneModel(final URL fileURL) throws ModelFormatException
    {
        try{
            final ZipInputStream zipInput = new ZipInputStream(fileURL.openStream());

            ZipEntry entry;
            while ((entry = zipInput.getNextEntry()) != null){
                final byte[] data = new byte[(int) entry.getSize()];
                // For some reason, using read(byte[]) makes reading stall upon reaching a 0x1E byte
                int i = 0;
                while ((zipInput.available() > 0) && (i < data.length)){
                    data[i++] = (byte) zipInput.read();
                }
                this.zipContents.put(entry.getName(), data);
            }

            final byte[] modelXml = this.zipContents.get("model.xml");
            if (modelXml == null){
                throw new ModelFormatException("Model " + this.fileName + " contains no model.xml file");
            }

            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.parse(new ByteArrayInputStream(modelXml));

            final NodeList nodeListTechne = document.getElementsByTagName("Techne");
            if (nodeListTechne.getLength() < 1){
                throw new ModelFormatException("Model " + this.fileName + " contains no Techne tag");
            }

            final NodeList nodeListModel = document.getElementsByTagName("Model");
            if (nodeListModel.getLength() < 1){
                throw new ModelFormatException("Model " + this.fileName + " contains no Model tag");
            }

            final NamedNodeMap modelAttributes = nodeListModel.item(0).getAttributes();
            if (modelAttributes == null){
                throw new ModelFormatException("Model " + this.fileName + " contains a Model tag with no attributes");
            }

            final Node modelTexture = modelAttributes.getNamedItem("texture");
            if (modelTexture != null){
                this.texture = modelTexture.getTextContent();
            }

            final NodeList shapes = document.getElementsByTagName("Shape");
            for (int i = 0; i < shapes.getLength(); i++){
                final Node shape = shapes.item(i);
                final NamedNodeMap shapeAttributes = shape.getAttributes();
                if (shapeAttributes == null){
                    throw new ModelFormatException("Shape #" + (i + 1) + " in " + this.fileName + " has no attributes");
                }

                final Node name = shapeAttributes.getNamedItem("name");
                String shapeName = null;
                if (name != null){
                    shapeName = name.getNodeValue();
                }
                if (shapeName == null){
                    shapeName = "Shape #" + (i + 1);
                }

                String shapeType = null;
                final Node type = shapeAttributes.getNamedItem("type");
                if (type != null){
                    shapeType = type.getNodeValue();
                }
                if ((shapeType != null) && !cubeTypes.contains(shapeType)){
                    FMLLog.warning("Model shape [" + shapeName + "] in " + this.fileName + " is not a cube, ignoring");
                    continue;
                }

                try{
                    boolean mirrored = false;
                    String[] offset = new String[3];
                    String[] position = new String[3];
                    String[] rotation = new String[3];
                    String[] size = new String[3];
                    String[] textureOffset = new String[2];

                    final NodeList shapeChildren = shape.getChildNodes();
                    for (int j = 0; j < shapeChildren.getLength(); j++){
                        final Node shapeChild = shapeChildren.item(j);

                        final String shapeChildName = shapeChild.getNodeName();
                        String shapeChildValue = shapeChild.getTextContent();
                        if (shapeChildValue != null){
                            shapeChildValue = shapeChildValue.trim();

                            if (shapeChildName.equals("IsMirrored")){
                                mirrored = !shapeChildValue.equals("False");
                            }else if (shapeChildName.equals("Offset")){
                                offset = shapeChildValue.split(",");
                            }else if (shapeChildName.equals("Position")){
                                position = shapeChildValue.split(",");
                            }else if (shapeChildName.equals("Rotation")){
                                rotation = shapeChildValue.split(",");
                            }else if (shapeChildName.equals("Size")){
                                size = shapeChildValue.split(",");
                            }else if (shapeChildName.equals("TextureOffset")){
                                textureOffset = shapeChildValue.split(",");
                            }
                        }
                    }

                    // That's what the ModelBase subclassing is needed for
                    final ModelRenderer cube = new ModelRenderer(this, Integer.parseInt(textureOffset[0]), Integer.parseInt(textureOffset[1]));
                    cube.mirror = mirrored;
                    cube.addBox(Float.parseFloat(offset[0]),
                                Float.parseFloat(offset[1]),
                                Float.parseFloat(offset[2]),
                                Integer.parseInt(size[0]),
                                Integer.parseInt(size[1]),
                                Integer.parseInt(size[2]));
                    cube.setRotationPoint(Float.parseFloat(position[0]), Float.parseFloat(position[1]) - 23.4F, Float.parseFloat(position[2]));

                    cube.rotateAngleX = (float) Math.toRadians(Float.parseFloat(rotation[0]));
                    cube.rotateAngleY = (float) Math.toRadians(Float.parseFloat(rotation[1]));
                    cube.rotateAngleZ = (float) Math.toRadians(Float.parseFloat(rotation[2]));

                    this.parts.put(shapeName, cube);
                }catch(final NumberFormatException e){
                    FMLLog.warning("Model shape [" + shapeName + "] in " + this.fileName + " contains malformed integers within its data, ignoring");
                    e.printStackTrace();
                }
            }
        }catch(final ZipException e){
            throw new ModelFormatException("Model " + this.fileName + " is not a valid zip file");
        }catch(final IOException e){
            throw new ModelFormatException("Model " + this.fileName + " could not be read", e);
        }catch(final ParserConfigurationException e){
            // hush
        }catch(final SAXException e){
            throw new ModelFormatException("Model " + this.fileName + " contains invalid XML", e);
        }
    }

    private void bindTexture()
    {
        if (this.texture != null){
            if (!this.textureNameSet){
                try{
                    final byte[] textureEntry = this.zipContents.get(this.texture);
                    if (textureEntry == null){
                        throw new ModelFormatException("Model " + this.fileName + " has no such texture " + this.texture);
                    }

                    final BufferedImage image = ImageIO.read(new ByteArrayInputStream(textureEntry));
                    this.textureName = Minecraft.getMinecraft().renderEngine.allocateAndSetupTexture(image);
                    this.textureNameSet = true;
                }catch(final ZipException e){
                    throw new ModelFormatException("Model " + this.fileName + " is not a valid zip file");
                }catch(final IOException e){
                    throw new ModelFormatException("Texture for model " + this.fileName + " could not be read", e);
                }
            }

            if (this.textureNameSet){
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureName);
                Minecraft.getMinecraft().renderEngine.resetBoundTexture();
            }
        }
    }

    @Override
    public String getType()
    {
        return "tcn";
    }

    @Override
    public void renderAll()
    {
        this.bindTexture();

        for (final ModelRenderer part : this.parts.values()){
            part.renderWithRotation(1.0F);
        }
    }

    @Override
    public void renderPart(final String partName)
    {
        final ModelRenderer part = this.parts.get(partName);
        if (part != null){
            this.bindTexture();
            part.renderWithRotation(1.0F);
        }
    }
}