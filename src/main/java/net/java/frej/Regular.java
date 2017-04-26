/*
Copyright 2011 Rodion Gorkovenko

This file is a part of FREJ
(project FREJ - Fuzzy Regular Expressions for Java - http://frej.sf.net)

FREJ is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

FREJ is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with FREJ.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.java.frej;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Regular extends Elem {
    
    private final Pattern pattern;
    private Matcher matcher;
    
    
    Regular(Regex owner, String pattern) {
        super(owner);
        this.pattern = Pattern.compile(pattern);
    } // Regular
    
    
    @Override
    double matchAt(int i) {
        matchStart = i;
        matchLen = 0;
        
        if (i >= owner.tokens.length) {
            return Double.POSITIVE_INFINITY;
        } // if
        
        matcher = pattern.matcher(owner.tokens[i]);
        if (!matcher.matches()) {
            return Double.POSITIVE_INFINITY;
        } // if
        
        matchLen = 1;
        
        saveGroup();
        
        return 0;
    } // matchAt
    
    @Override
    void saveGroup() {
        super.saveGroup();
        
        if (group == null || group.isEmpty()) {
            return;
        } // if
        
        for (int grpIdx = 1; grpIdx <= matcher.groupCount(); grpIdx++) {
            owner.setGroup(group + grpIdx, matcher.group(grpIdx));
        } // for
    }
    
    
    @Override
    public String toString() {
        return "(!" + pattern.pattern() + ")" + super.toString();
    } // toString
    
    
} // Regular
