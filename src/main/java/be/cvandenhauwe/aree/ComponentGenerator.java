/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class ComponentGenerator {
     public static void main(String[] args) throws IOException {
        generatePOMs();
    }
     
    public static void generateComponents() throws IOException{
        for(int i = 1; i <= 100; i++){
            FileWriter writer = new FileWriter("/Users/caroline/Desktop/components/be/cvandenhauwe/aree/experimentcomponents/ExperimentThreeComponent" + i + ".java");
            writer.append("package be.cvandenhauwe.aree.experimentcomponents;\n");
            writer.append("import be.cvandenhauwe.aree.configuration.AreeArguments;\n");
            writer.append("import be.cvandenhauwe.aree.configuration.AreeComponentInterface;\n");
            
            writer.append("public class ExperimentThreeComponent" + i + " implements AreeComponentInterface {\n");
            writer.append("private String time;\n");
            
            writer.append("@Override\npublic Object process(AreeArguments runtimeArguments, Object obj) throws Exception {\n");
            writer.append("return time;\n}\n");
            
            writer.append("@Override\npublic void setup(AreeArguments setupArguments) throws Exception {\n");
            writer.append("time = setupArguments.getValue(\"time\") + \"\";\n}\n}");
            
            writer.flush();
            writer.close();
        }
    }
    
    public static void generatePOMs() throws IOException{
        for(int i = 1; i <= 100; i++){
            FileWriter writer = new FileWriter("/Users/caroline/Desktop/components/poms/pom" + i + ".xml");
            writer.append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">");
            writer.append("  <modelVersion>4.0.0</modelVersion>"); 
            writer.append("  <groupId>be.cvandenhauwe.aree</groupId>");
            writer.append("  <artifactId>my-component"+ i + "</artifactId>");
            writer.append("  <packaging>jar</packaging>");
            writer.append("  <version>1.0-SNAPSHOT</version>");
            writer.append("  <name>my-component"+ i + "</name>");
            writer.append("  <url>http://maven.apache.org</url>");
            writer.append("  <dependencies>");
            writer.append("	<dependency>");
            writer.append("      <groupId>be.cvandenhauwe</groupId>");
            writer.append("      <artifactId>Aree-api</artifactId>");
            writer.append("      <version>1.0-SNAPSHOT</version>");
            writer.append("      <type>jar</type>");
            writer.append("    </dependency>");
            writer.append("    <dependency>");
            writer.append("      <groupId>junit</groupId>");
            writer.append("      <artifactId>junit</artifactId>");
            writer.append("      <version>3.8.1</version>");
            writer.append("      <scope>test</scope>");
            writer.append("    </dependency>"); 
            writer.append("  </dependencies>");
            writer.append("</project>");
            writer.flush();
            writer.close();
        }
    }
}
