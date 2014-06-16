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
 *  (c) copyright Desmond Schmidt 2014
 */

package tilt.test;

import tilt.test.html.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tilt.exception.TiltException;

/**
 * Test the POST command
 * @author desmond
 */
public class Post extends Test
{
    static String POST_JS = 
    "function getUrl(geojson)\n{\nvar obj = JSON.parse(geojson);\n"
    +"if ( obj && obj.properties.url )\n\treturn obj.properties.ur"
    +"l;\nelse\n\treturn \"http://setis.library.usyd.edu.au/ozedit"
    +"s/harpur/A87-1/00000005.jpg\";\n}\nfunction escapeUrl( url )"
    +"\n{\nvar newUrl = \"\";\nfor ( var i=0;i<url.length;i++ )\n{"
    +"\n\tvar token = url.charAt(i);\n\tswitch ( token )\n\t{\n\tc"
    +"ase '/':\n\t\tnewUrl += \"%2F\";\n\t\tbreak;\n\tcase ' ':\n\t"
    +"\tnewUrl += '%20';\n\t\tbreak;\n\tcase ':':\n\t\tnewUrl += "
    +"'%3A';\n\t\tbreak;\n\tdefault:\n\t\tnewUrl += url.charAt(i);"
    +"\n\t\tbreak;\n\t}\n}\nreturn newUrl;\n}\nfunction getUrlFrom"
    +"Loc( pictype )\n{\n\tvar gjurl = getUrl($(\"#geojson\").val("
    +"));\n\t\tvar url = \"../?docid=\"+escapeUrl(gjurl)+\"&pictyp"
    +"e=\"+pictype;\n\talert(url);\n\treturn url;\n}\n$(document)."
    +"ready(function() {\n$(\"#upload\").click(function(e){\n\t$.p"
    +"ost( $(\"#main\").attr(\"action\"), $(\"#main\").serialize()"
    +", function( data ) {\n\t$(\"#left\").empty();\n\t$(\"#left\""
    +").html(data);\n\t},\"text\").fail(function(xhr, status, erro"
    +"r){\n\talert(status);\n\t});\n});\n$(\"#original\").click(fu"
    +"nction(){\n\tvar url = escapeUrl(getUrlFromLoc(\"original\")"
    +");\n\t$(\"#left\").empty();\n\t$(\"#left\").html('<img width"
    +"=\"500\" src=\"'+url+'\">');\n});\n$(\"#greyscale\").click(f"
    +"unction(){\n\tvar url = escapeUrl(getUrlFromLoc(\"greyscale\""
    +"));\n\t$(\"#left\").empty();\n\t$(\"#left\").html('<img wid"
    +"th=\"500\" src=\"'+getUrlFromLoc(\"greyscale\")+'\">');\n});"
    +"\n$(\"#twotone\").click(function(){\n\tvar url = escapeUrl(g"
    +"etUrlFromLoc(\"twotone\"));\n\t$(\"#left\").empty();\n\t$(\""
    +"#left\").html('<img width=\"500\" src=\"'+getUrlFromLoc(\"tw"
    +"otone\")+'\">');\n});\n}); // end doc ready\n";
    static String DEFAULT_JSON = 
    "{\n\"type\": \"Feature\",\n\"geometry\": {\n\t\"type\": \"Pol"
    +"ygon\",\n\t\"coordinates\": [\n\t[ [0.0, 0.0], [100.0, 0.0],"
    +" [100.0, 100.0], [0.0, 100.0] ]\n\t]\n},\n\"properties\": {\n"
    +"\t\"url\": \"http://setis.library.usyd.edu.au/ozedits/harpu"
    +"r/A87-1/00000005.jpg\"\n}\n}\n";
    static String POST_CSS =
    "#left {float:left;margin:10px}\n" +
    "#right {float:left;margin:10px}\n" +
    "#toolbar {clear:both}";
    /**
     * Display the test GUI
     * @param request the request to read from
     * @param urn the original URN
     * @return a formatted html String
     */
    @Override
    public void handle( HttpServletRequest request,
        HttpServletResponse response, String urn ) throws TiltException
    {
        try
        {
            // create the doc and install the scripts etc
            doc = new HTML();
            doc.getHead().addEncoding("text/html; charset=UTF-8");
            doc.getHead().addJQuery("1.9.0");
            doc.getHead().addScript( POST_JS );
            doc.getHead().addCss(POST_CSS);
            Element h1 = new Element("h1");
            h1.addText("TILT Upload test");
            doc.addElement( h1 );
            Form f = new Form( "POST", "http://localhost:8080/", 
                "multipart/form-data" );
            f.addAttribute("id","main");
            Element p = new Element("p");
            p.addText("Enter/edit GeoJSON:");
            f.addElement( p );
            Element div1 = new Element("div");
            div1.addAttribute("id","left");
            doc.addElement( div1 );
            Element div2 = new Element("div");
            div2.addAttribute("id","right");
            Element textarea = new Element("textarea");
            textarea.addAttribute("name","geojson");
            textarea.addAttribute("id","geojson");
            textarea.addAttribute("rows","12");
            textarea.addAttribute("cols","80");
            textarea.addText(DEFAULT_JSON);
            f.addElement( textarea );
            p = new Element("p");
            p.addElement( new Input("upload","button","upload") );
            p.addElement( new Input("original","button","original") );
            p.addElement( new Input("greyscale","button","greyscale") );
            p.addElement( new Input("twotone","button","two tone") );
            f.addElement( p );
            doc.addElement( f );
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(doc);
        }
        catch ( Exception e )
        {
            throw new TiltException(e);
        }
    }
}
