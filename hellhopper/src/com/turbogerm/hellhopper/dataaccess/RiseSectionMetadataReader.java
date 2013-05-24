/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.dataaccess;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.turbogerm.hellhopper.util.Logger;

public final class RiseSectionMetadataReader {
    
    public static RiseSectionsMetadata read(FileHandle fileHandle) {
        
        XmlReader reader = new XmlReader();
        
        Element riseSectionsMetaNode = null;
        try {
            riseSectionsMetaNode = reader.parse(fileHandle);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return null;
        }
        
        Element riseSectionsNode = riseSectionsMetaNode.getChildByName("risesections");
        
        int numRiseSections = riseSectionsNode.getChildCount();
        Array<RiseSectionMetadata> riseSectionMetadataList = new Array<RiseSectionMetadata>(true, numRiseSections);
        for (int i = 0; i < numRiseSections; i++) {
            Element riseSectionNode = riseSectionsNode.getChild(i);
            RiseSectionMetadata riseSection = getRiseSectionMetadata(riseSectionNode);
            riseSectionMetadataList.add(riseSection);
        }
        
        return new RiseSectionsMetadata(riseSectionMetadataList);
    }
    
    private static RiseSectionMetadata getRiseSectionMetadata(Element riseSection) {
        String type = riseSection.getAttribute("type");
        String name = riseSection.getAttribute("name");
        int minStepRange = Integer.parseInt(riseSection.getAttribute("minsteprange"));
        int maxStepRange = Integer.parseInt(riseSection.getAttribute("maxsteprange"));
        int minStepDistance = Integer.parseInt(riseSection.getAttribute("minstepdistance"));
        int maxStepDistance = Integer.parseInt(riseSection.getAttribute("maxstepdistance"));
        int difficulty = Integer.parseInt(riseSection.getAttribute("difficulty"));
        
        ObjectMap<String, String> properties =  ReaderUtilities.getProperties(
                riseSection.getChildByName("properties"));
        
        return new RiseSectionMetadata(type, name,
                minStepRange, maxStepRange, minStepDistance, maxStepDistance,
                difficulty, properties);
    }
}
