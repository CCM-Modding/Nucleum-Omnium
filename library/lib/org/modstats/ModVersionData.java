/**
 * Copyright (c) <2012>, Oleg Romanovskiy <shedarhome@gmail.com> aka Shedar
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the author nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lib.org.modstats;

import java.util.HashMap;
import java.util.Map;

public class ModVersionData {

    public String              prefix;

    public String              name;

    public String              version;

    public String              downloadUrl;

    public String              changeLogUrl;

    public Map<String, String> extraFields;

    public ModVersionData() {
        this.extraFields = new HashMap<String, String>();
    }

    public ModVersionData(final String prefix, final String name, final String version) {
        this.prefix = prefix;
        this.name = name;
        this.version = version;
        this.extraFields = new HashMap<String, String>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.changeLogUrl == null ? 0 : this.changeLogUrl.hashCode());
        result = prime * result + (this.downloadUrl == null ? 0 : this.downloadUrl.hashCode());
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + (this.prefix == null ? 0 : this.prefix.hashCode());
        result = prime * result + (this.version == null ? 0 : this.version.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final ModVersionData other = (ModVersionData) obj;
        if (this.changeLogUrl == null) {
            if (other.changeLogUrl != null)
                return false;
        } else if (!this.changeLogUrl.equals(other.changeLogUrl))
            return false;
        if (this.downloadUrl == null) {
            if (other.downloadUrl != null)
                return false;
        } else if (!this.downloadUrl.equals(other.downloadUrl))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (this.prefix == null) {
            if (other.prefix != null)
                return false;
        } else if (!this.prefix.equals(other.prefix))
            return false;
        if (this.version == null) {
            if (other.version != null)
                return false;
        } else if (!this.version.equals(other.version))
            return false;
        return true;
    }

}