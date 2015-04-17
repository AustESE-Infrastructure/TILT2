/*
 * This file is part of TILT.
 *
 *  TILT is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  TILT is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with TILT.  If not, see <http://www.gnu.org/licenses/>.
 *  (c) copyright Desmond Schmidt 2015
 */     

package tilt.handler.post;
import org.json.simple.*;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

/**
 * Store options for TILT2
 * @author desmond
 */
public class Options extends HashMap<Options.Keys,Object>
{
    private void setDefaults()
    {
        this.put(Keys.minBlackPC,0.5);
        // standoff around speckles
        this.put(Keys.whiteStandoff,0.02);
        // speckle size in margins only
        this.put(Keys.speckleSize, 0.005); 
        this.put(Keys.oddShape,4.0);
        /** maximum amount times line depth to accept new lines */
        this.put(Keys.lineDepthFactor,1.0);
        this.put(Keys.blueFactor,0.3);
        this.put(Keys.maximumWidth,1200);
    }
    /**
     * Initialise an options object from a possibly empty set of properties
     * @param opts a RAW JSON object
     */
    public Options( JSONObject opts )
    {
        setDefaults();
        HashMap<String,Object> map = (HashMap)opts;
        Set<String> keys = map.keySet();
        Iterator<String> iter = keys.iterator();
        while ( iter.hasNext() )
        {
            try
            {
                Options.Keys key = Options.Keys.valueOf(iter.next());
                this.put(key, map.get(key));
            }
            catch( Exception e )
            {
            }
        }
    }
    public enum Keys
    {
        minBlackPC,
        whiteStandoff,
        oddShape,
        lineDepthFactor,
        speckleSize,
        blueFactor,
        maximumWidth;
    }
    public float getFloat( Keys key ) throws NumberFormatException
    {
        Object obj = this.get(key);
        if ( obj instanceof Number )
            return ((Number)obj).floatValue();
        else
            throw new NumberFormatException("Invalid option key "+key);
    }
    public int getInt( Keys key ) throws NumberFormatException
    {
        Object obj = this.get(key);
        if ( obj instanceof Number )
            return ((Number)obj).intValue();
        else
            throw new NumberFormatException("Invalid option key "+key);
    }
    // add methods to cover other data types
}
