/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.codehaus.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Ant" and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact codehaus@codehaus.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.codehaus.org/>.
 */

package ccm.libs.org.codehaus.plexus.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>This is a utility class used by selectors and DirectoryScanner. The
 * functionality more properly belongs just to selectors, but unfortunately
 * DirectoryScanner exposed these as protected methods. Thus we have to
 * support any subclasses of DirectoryScanner that may access these methods.
 * </p>
 * <p>This is a Singleton.</p>
 *
 * @author Arnout J. Kuiper
 *         <a href="mailto:ajkuiper@wxs.nl">ajkuiper@wxs.nl</a>
 * @author Magesh Umasankar
 * @author <a href="mailto:bruce@callenish.com">Bruce Atherton</a>
 * @version $Id$
 * @since 1.5
 */
public final class SelectorUtils
{

    public static final String PATTERN_HANDLER_PREFIX = "[";

    public static final String PATTERN_HANDLER_SUFFIX = "]";

    public static final String REGEX_HANDLER_PREFIX = "%regex" + PATTERN_HANDLER_PREFIX;

    public static final String ANT_HANDLER_PREFIX = "%ant" + PATTERN_HANDLER_PREFIX;

    private static SelectorUtils instance = new SelectorUtils();

    /**
     * Private Constructor
     */
    private SelectorUtils()
    {
    }

    /**
     * Retrieves the manager of the Singleton.
     */
    public static SelectorUtils getInstance()
    {
        return instance;
    }

    /**
     * Tests whether or not a given path matches the start of a given
     * pattern up to the first "**".
     * <p/>
     * This is not a general purpose test and should only be used if you
     * can live with false positives. For example, <code>pattern=**\a</code>
     * and <code>str=b</code> will yield <code>true</code>.
     *
     * @param pattern The pattern to match against. Must not be
     *                <code>null</code>.
     * @param str     The path to match, as a String. Must not be
     *                <code>null</code>.
     * @return whether or not a given path matches the start of a given
     *         pattern up to the first "**".
     */
    public static boolean matchPatternStart( String pattern, String str )
    {
        return matchPatternStart( pattern, str, true );
    }

    /**
     * Tests whether or not a given path matches the start of a given
     * pattern up to the first "**".
     * <p/>
     * This is not a general purpose test and should only be used if you
     * can live with false positives. For example, <code>pattern=**\a</code>
     * and <code>str=b</code> will yield <code>true</code>.
     *
     * @param pattern         The pattern to match against. Must not be
     *                        <code>null</code>.
     * @param str             The path to match, as a String. Must not be
     *                        <code>null</code>.
     * @param isCaseSensitive Whether or not matching should be performed
     *                        case sensitively.
     * @return whether or not a given path matches the start of a given
     *         pattern up to the first "**".
     */
    public static boolean matchPatternStart( String pattern, String str, boolean isCaseSensitive )
    {
        if ( isRegexPrefixedPattern( pattern ) )
        {
            // FIXME: ICK! But we can't do partial matches for regex, so we have to reserve judgement until we have
            // a file to deal with, or we can definitely say this is an exclusion...
            return true;
        }
        else
        {
            if ( isAntPrefixedPattern( pattern ) )
            {
                pattern = pattern.substring( ANT_HANDLER_PREFIX.length(),
                                             pattern.length() - PATTERN_HANDLER_SUFFIX.length() );
            }

            String altStr = str.replace( '\\', '/' );

            return matchAntPathPatternStart( pattern, str, File.separator, isCaseSensitive )
                || matchAntPathPatternStart( pattern, altStr, "/", isCaseSensitive );
        }
    }

    static boolean isAntPrefixedPattern( String pattern )
    {
        return pattern.length() > ( ANT_HANDLER_PREFIX.length() + PATTERN_HANDLER_SUFFIX.length() + 1 )
            && pattern.startsWith( ANT_HANDLER_PREFIX ) && pattern.endsWith( PATTERN_HANDLER_SUFFIX );
    }

    @SuppressWarnings( "SimplifiableIfStatement" )
    static boolean matchAntPathPatternStart( MatchPattern pattern, String str, String separator,
                                             boolean isCaseSensitive )
    {
        if ( separatorPatternStartSlashMismatch( pattern, str, separator ) )
        {
            return false;
        }

        return matchAntPathPatternStart( pattern.getTokenizedPathString(), str, separator, isCaseSensitive );
    }

    static boolean matchAntPathPatternStart( String pattern, String str, String separator, boolean isCaseSensitive )
    {
        // When str starts with a File.separator, pattern has to start with a
        // File.separator.
        // When pattern starts with a File.separator, str has to start with a
        // File.separator.
        if ( separatorPatternStartSlashMismatch( pattern, str, separator ) )
        {
            return false;
        }

        String[] patDirs = tokenizePathToString( pattern, separator );
        return matchAntPathPatternStart( patDirs, str, separator, isCaseSensitive );
    }

    // When str starts with a File.separator, pattern has to start with a
    // File.separator.
    // When pattern starts with a File.separator, str has to start with a
    // File.separator.
    private static boolean separatorPatternStartSlashMismatch( String pattern, String str, String separator )
    {
        return str.startsWith( separator ) != pattern.startsWith( separator );
    }

    private static boolean separatorPatternStartSlashMismatch( MatchPattern matchPattern, String str, String separator )
    {
        return str.startsWith( separator ) != matchPattern.startsWith( separator );
    }


    static boolean matchAntPathPatternStart( String[] patDirs, String str, String separator, boolean isCaseSensitive )
    {
        String[] strDirs = tokenizePathToString( str, separator );

        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strDirs.length - 1;

        // up to first '**'
        while ( patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd )
        {
            String patDir = patDirs[patIdxStart];
            if ( patDir.equals( "**" ) )
            {
                break;
            }
            if ( !match( patDir, strDirs[strIdxStart], isCaseSensitive ) )
            {
                return false;
            }
            patIdxStart++;
            strIdxStart++;
        }

        return strIdxStart > strIdxEnd || patIdxStart <= patIdxEnd;
    }

    /**
     * Tests whether or not a given path matches a given pattern.
     *
     * @param pattern The pattern to match against. Must not be
     *                <code>null</code>.
     * @param str     The path to match, as a String. Must not be
     *                <code>null</code>.
     * @return <code>true</code> if the pattern matches against the string,
     *         or <code>false</code> otherwise.
     */
    public static boolean matchPath( String pattern, String str )
    {
        return matchPath( pattern, str, true );
    }

    /**
     * Tests whether or not a given path matches a given pattern.
     *
     * @param pattern         The pattern to match against. Must not be
     *                        <code>null</code>.
     * @param str             The path to match, as a String. Must not be
     *                        <code>null</code>.
     * @param isCaseSensitive Whether or not matching should be performed
     *                        case sensitively.
     * @return <code>true</code> if the pattern matches against the string,
     *         or <code>false</code> otherwise.
     */
    public static boolean matchPath( String pattern, String str, boolean isCaseSensitive )
    {
        return matchPath( pattern, str, File.separator, isCaseSensitive );
    }

    public static boolean matchPath( String pattern, String str, String separator, boolean isCaseSensitive )
    {
        if ( isRegexPrefixedPattern( pattern ) )
        {
            pattern =
                pattern.substring( REGEX_HANDLER_PREFIX.length(), pattern.length() - PATTERN_HANDLER_SUFFIX.length() );

            return str.matches( pattern );
        }
        else
        {
            if ( isAntPrefixedPattern( pattern ) )
            {
                pattern = pattern.substring( ANT_HANDLER_PREFIX.length(),
                                             pattern.length() - PATTERN_HANDLER_SUFFIX.length() );
            }

            return matchAntPathPattern( pattern, str, separator, isCaseSensitive );
        }
    }

    static boolean isRegexPrefixedPattern( String pattern )
    {
        return pattern.length() > ( REGEX_HANDLER_PREFIX.length() + PATTERN_HANDLER_SUFFIX.length() + 1 )
            && pattern.startsWith( REGEX_HANDLER_PREFIX ) && pattern.endsWith( PATTERN_HANDLER_SUFFIX );
    }

    static boolean matchAntPathPattern( MatchPattern matchPattern, String str, String separator,
                                        boolean isCaseSensitive )
    {
        if ( separatorPatternStartSlashMismatch( matchPattern, str, separator ) )
        {
            return false;
        }
        String[] patDirs = matchPattern.getTokenizedPathString();
        String[] strDirs = tokenizePathToString( str, separator );
        return matchAntPathPattern( patDirs, strDirs, isCaseSensitive );
    }

    static boolean matchAntPathPattern( String pattern, String str, String separator, boolean isCaseSensitive )
    {
        if ( separatorPatternStartSlashMismatch( pattern, str, separator ) )
        {
            return false;
        }
        String[] patDirs = tokenizePathToString( pattern, separator );
        String[] strDirs = tokenizePathToString( str, separator );
        return matchAntPathPattern( patDirs, strDirs, isCaseSensitive );

    }

    static boolean matchAntPathPattern( String[] patDirs, String[] strDirs, boolean isCaseSensitive )
    {
        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strDirs.length - 1;

        // up to first '**'
        while ( patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd )
        {
            String patDir = patDirs[patIdxStart];
            if ( patDir.equals( "**" ) )
            {
                break;
            }
            if ( !match( patDir, strDirs[strIdxStart], isCaseSensitive ) )
            {
                return false;
            }
            patIdxStart++;
            strIdxStart++;
        }
        if ( strIdxStart > strIdxEnd )
        {
            // String is exhausted
            for ( int i = patIdxStart; i <= patIdxEnd; i++ )
            {
                if ( !patDirs[i].equals( "**" ) )
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            if ( patIdxStart > patIdxEnd )
            {
                // String not exhausted, but pattern is. Failure.
                return false;
            }
        }

        // up to last '**'
        while ( patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd )
        {
            String patDir = patDirs[patIdxEnd];
            if ( patDir.equals( "**" ) )
            {
                break;
            }
            if ( !match( patDir, strDirs[strIdxEnd], isCaseSensitive ) )
            {
                return false;
            }
            patIdxEnd--;
            strIdxEnd--;
        }
        if ( strIdxStart > strIdxEnd )
        {
            // String is exhausted
            for ( int i = patIdxStart; i <= patIdxEnd; i++ )
            {
                if ( !patDirs[i].equals( "**" ) )
                {
                    return false;
                }
            }
            return true;
        }

        while ( patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd )
        {
            int patIdxTmp = -1;
            for ( int i = patIdxStart + 1; i <= patIdxEnd; i++ )
            {
                if ( patDirs[i].equals( "**" ) )
                {
                    patIdxTmp = i;
                    break;
                }
            }
            if ( patIdxTmp == patIdxStart + 1 )
            {
                // '**/**' situation, so skip one
                patIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = ( patIdxTmp - patIdxStart - 1 );
            int strLength = ( strIdxEnd - strIdxStart + 1 );
            int foundIdx = -1;
            strLoop:
            for ( int i = 0; i <= strLength - patLength; i++ )
            {
                for ( int j = 0; j < patLength; j++ )
                {
                    String subPat = patDirs[patIdxStart + j + 1];
                    String subStr = strDirs[strIdxStart + i + j];
                    if ( !match( subPat, subStr, isCaseSensitive ) )
                    {
                        continue strLoop;
                    }
                }

                foundIdx = strIdxStart + i;
                break;
            }

            if ( foundIdx == -1 )
            {
                return false;
            }

            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }

        for ( int i = patIdxStart; i <= patIdxEnd; i++ )
        {
            if ( !patDirs[i].equals( "**" ) )
            {
                return false;
            }
        }

        return true;
    }

    static boolean matchAntPathPattern( char[][] patDirs, char[][] strDirs, boolean isCaseSensitive )
    {
        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strDirs.length - 1;

        // up to first '**'
        while ( patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd )
        {
            char[] patDir = patDirs[patIdxStart];
            if ( isDoubleStar( patDir ) )
            {
                break;
            }
            if ( !match( patDir, strDirs[strIdxStart], isCaseSensitive ) )
            {
                return false;
            }
            patIdxStart++;
            strIdxStart++;
        }
        if ( strIdxStart > strIdxEnd )
        {
            // String is exhausted
            for ( int i = patIdxStart; i <= patIdxEnd; i++ )
            {
                if ( !isDoubleStar( patDirs[i] ) )
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            if ( patIdxStart > patIdxEnd )
            {
                // String not exhausted, but pattern is. Failure.
                return false;
            }
        }

        // up to last '**'
        while ( patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd )
        {
            char[] patDir = patDirs[patIdxEnd];
            if ( isDoubleStar( patDir ) )
            {
                break;
            }
            if ( !match( patDir, strDirs[strIdxEnd], isCaseSensitive ) )
            {
                return false;
            }
            patIdxEnd--;
            strIdxEnd--;
        }
        if ( strIdxStart > strIdxEnd )
        {
            // String is exhausted
            for ( int i = patIdxStart; i <= patIdxEnd; i++ )
            {
                if ( !isDoubleStar( patDirs[i] ) )
                {
                    return false;
                }
            }
            return true;
        }

        while ( patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd )
        {
            int patIdxTmp = -1;
            for ( int i = patIdxStart + 1; i <= patIdxEnd; i++ )
            {
                if ( isDoubleStar( patDirs[i] ) )
                {
                    patIdxTmp = i;
                    break;
                }
            }
            if ( patIdxTmp == patIdxStart + 1 )
            {
                // '**/**' situation, so skip one
                patIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = ( patIdxTmp - patIdxStart - 1 );
            int strLength = ( strIdxEnd - strIdxStart + 1 );
            int foundIdx = -1;
            strLoop:
            for ( int i = 0; i <= strLength - patLength; i++ )
            {
                for ( int j = 0; j < patLength; j++ )
                {
                    char[] subPat = patDirs[patIdxStart + j + 1];
                    char[] subStr = strDirs[strIdxStart + i + j];
                    if ( !match( subPat, subStr, isCaseSensitive ) )
                    {
                        continue strLoop;
                    }
                }

                foundIdx = strIdxStart + i;
                break;
            }

            if ( foundIdx == -1 )
            {
                return false;
            }

            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }

        for ( int i = patIdxStart; i <= patIdxEnd; i++ )
        {
            if ( !isDoubleStar( patDirs[i] ) )
            {
                return false;
            }
        }

        return true;
    }

    private static boolean isDoubleStar( char[] patDir )
    {
        return patDir != null && patDir.length == 2 && patDir[0] == '*' && patDir[1] == '*';
    }

    /**
     * Tests whether or not a string matches against a pattern.
     * The pattern may contain two special characters:<br>
     * '*' means zero or more characters<br>
     * '?' means one and only one character
     *
     * @param pattern The pattern to match against.
     *                Must not be <code>null</code>.
     * @param str     The string which must be matched against the pattern.
     *                Must not be <code>null</code>.
     * @return <code>true</code> if the string matches against the pattern,
     *         or <code>false</code> otherwise.
     */
    public static boolean match( String pattern, String str )
    {
        return match( pattern, str, true );
    }

    /**
     * Tests whether or not a string matches against a pattern.
     * The pattern may contain two special characters:<br>
     * '*' means zero or more characters<br>
     * '?' means one and only one character
     *
     * @param pattern         The pattern to match against.
     *                        Must not be <code>null</code>.
     * @param str             The string which must be matched against the pattern.
     *                        Must not be <code>null</code>.
     * @param isCaseSensitive Whether or not matching should be performed
     *                        case sensitively.
     * @return <code>true</code> if the string matches against the pattern,
     *         or <code>false</code> otherwise.
     */
    public static boolean match( String pattern, String str, boolean isCaseSensitive )
    {
        char[] patArr = pattern.toCharArray();
        char[] strArr = str.toCharArray();
        return match( patArr, strArr, isCaseSensitive);
    }

    public static boolean match( char[] patArr, char[] strArr, boolean isCaseSensitive )
    {
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strArr.length - 1;
        char ch;

        boolean containsStar = false;
        for ( char aPatArr : patArr )
        {
            if ( aPatArr == '*' )
            {
                containsStar = true;
                break;
            }
        }

        if ( !containsStar )
        {
            // No '*'s, so we make a shortcut
            if ( patIdxEnd != strIdxEnd )
            {
                return false; // Pattern and string do not have the same size
            }
            for ( int i = 0; i <= patIdxEnd; i++ )
            {
                ch = patArr[i];
                if ( ch != '?' && !equals( ch, strArr[i], isCaseSensitive ) )
                {
                    return false; // Character mismatch
                }
            }
            return true; // String matches against pattern
        }

        if ( patIdxEnd == 0 )
        {
            return true; // Pattern contains only '*', which matches anything
        }

        // Process characters before first star
        while ( ( ch = patArr[patIdxStart] ) != '*' && strIdxStart <= strIdxEnd )
        {
            if ( ch != '?' && !equals( ch, strArr[strIdxStart], isCaseSensitive ) )
            {
                return false; // Character mismatch
            }
            patIdxStart++;
            strIdxStart++;
        }
        if ( strIdxStart > strIdxEnd )
        {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            for ( int i = patIdxStart; i <= patIdxEnd; i++ )
            {
                if ( patArr[i] != '*' )
                {
                    return false;
                }
            }
            return true;
        }

        // Process characters after last star
        while ( ( ch = patArr[patIdxEnd] ) != '*' && strIdxStart <= strIdxEnd )
        {
            if ( ch != '?' && !equals( ch, strArr[strIdxEnd], isCaseSensitive ) )
            {
                return false; // Character mismatch
            }
            patIdxEnd--;
            strIdxEnd--;
        }
        if ( strIdxStart > strIdxEnd )
        {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            for ( int i = patIdxStart; i <= patIdxEnd; i++ )
            {
                if ( patArr[i] != '*' )
                {
                    return false;
                }
            }
            return true;
        }

        // process pattern between stars. padIdxStart and patIdxEnd point
        // always to a '*'.
        while ( patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd )
        {
            int patIdxTmp = -1;
            for ( int i = patIdxStart + 1; i <= patIdxEnd; i++ )
            {
                if ( patArr[i] == '*' )
                {
                    patIdxTmp = i;
                    break;
                }
            }
            if ( patIdxTmp == patIdxStart + 1 )
            {
                // Two stars next to each other, skip the first one.
                patIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = ( patIdxTmp - patIdxStart - 1 );
            int strLength = ( strIdxEnd - strIdxStart + 1 );
            int foundIdx = -1;
            strLoop:
            for ( int i = 0; i <= strLength - patLength; i++ )
            {
                for ( int j = 0; j < patLength; j++ )
                {
                    ch = patArr[patIdxStart + j + 1];
                    if ( ch != '?' && !equals( ch, strArr[strIdxStart + i + j], isCaseSensitive ) )
                    {
                        continue strLoop;
                    }
                }

                foundIdx = strIdxStart + i;
                break;
            }

            if ( foundIdx == -1 )
            {
                return false;
            }

            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }

        // All characters in the string are used. Check if only '*'s are left
        // in the pattern. If so, we succeeded. Otherwise failure.
        for ( int i = patIdxStart; i <= patIdxEnd; i++ )
        {
            if ( patArr[i] != '*' )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests whether two characters are equal.
     */
    private static boolean equals( char c1, char c2, boolean isCaseSensitive )
    {
        if ( c1 == c2 )
        {
            return true;
        }
        if ( !isCaseSensitive )
        {
            // NOTE: Try both upper case and lower case as done by String.equalsIgnoreCase()
            if ( Character.toUpperCase( c1 ) == Character.toUpperCase( c2 )
                || Character.toLowerCase( c1 ) == Character.toLowerCase( c2 ) )
            {
                return true;
            }
        }
        return false;
    }

    private static String[] tokenizePathToString( String path, String separator )
    {
        List<String> ret = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer( path, separator );
        while ( st.hasMoreTokens() )
        {
            ret.add( st.nextToken() );
        }
        return ret.toArray( new String[ret.size()] );
    }


    /**
     * Returns dependency information on these two files. If src has been
     * modified later than target, it returns true. If target doesn't exist,
     * it likewise returns true. Otherwise, target is newer than src and
     * is not out of date, thus the method returns false. It also returns
     * false if the src file doesn't even exist, since how could the
     * target then be out of date.
     *
     * @param src         the original file
     * @param target      the file being compared against
     * @param granularity the amount in seconds of slack we will give in
     *                    determining out of dateness
     * @return whether the target is out of date
     */
    public static boolean isOutOfDate( File src, File target, int granularity )
    {
        if ( !src.exists() )
        {
            return false;
        }
        if ( !target.exists() )
        {
            return true;
        }
        if ( ( src.lastModified() - granularity ) > target.lastModified() )
        {
            return true;
        }
        return false;
    }

    /**
     * "Flattens" a string by removing all whitespace (space, tab, linefeed,
     * carriage return, and formfeed). This uses StringTokenizer and the
     * default set of tokens as documented in the single arguement constructor.
     *
     * @param input a String to remove all whitespace.
     * @return a String that has had all whitespace removed.
     */
    public static String removeWhitespace( String input )
    {
        StringBuilder result = new StringBuilder();
        if ( input != null )
        {
            StringTokenizer st = new StringTokenizer( input );
            while ( st.hasMoreTokens() )
            {
                result.append( st.nextToken() );
            }
        }
        return result.toString();
    }
}
