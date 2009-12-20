package com.quui;

import java.io.File;
import java.util.regex.Pattern;

/** Just a very basic idea for transforming Java code to LiteratePrograms wiki syntax. */
public class Literizer {

	/** @param args The Java file to transform and the full output location */
	public static void main(String[] args) {
		File file = new File(args.length > 0 ? args[0].trim() : "src/com/quui/algorithms/string_matching/EditDistance.java");
        Util.saveString(args.length > 1 ? args[1].trim() : file.getParent()+ "/" + file.getName() + "_generated.mediawiki", transform(Util.getText(file)));
    }

	private static String transform(String in) {
        Pattern p = Pattern.compile("/\\* <<([^>]+)>>= \\*/(.+)(?=/\\* =<<)/\\* =<<\\1>> \\*/", Pattern.DOTALL | Pattern.MULTILINE);
		String out = p.matcher(in).replaceAll("\n<codeblock language=java>\n<<$1>>=\n\n$2</codeblock>\n<<$1>>"); System.out.println(out); return out;
	}

}
