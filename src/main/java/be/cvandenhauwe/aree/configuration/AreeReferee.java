/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeReferee {
        
//    @Produces AreeInput getInput(InjectionPoint ip){
//        System.out.println("in InjectInput: ");
//        return new MarkerInput();        
//    }
    
//    @PostConstruct
//    public void process(){        
//        System.out.println("Parsed from input: " + aiNames);
//        if(aiNames.size() <= 0) return; //nothing imported, can't do anything here
//        chooseComponents(ais, aiNames, true);      
//        System.out.println("Other end of input = " + ai.produceInput("test"));
//    }
    
    public Object process(AreeConfiguration configuration, String data) throws ComponentNotFoundException {
        //input
        Object inputDone = configuration.chooseInput().produceInput(data);
        //Object inputDone = configuration.getInput().produceInput(data);
        
        //reasoner        
        //Object reasonerDone = configuration.chooseReasoner().process(inputDone);
        //Object reasonerDone = configuration.getReasoner().process(inputDone);
        
        //output
        //return configuration.chooseOutput().produceOutput(reasonerDone);
        //return configuration.getOutput().produceOutput(reasonerDone);
        return "meh";
    }
    
//    private void chooseComponents(Instance<AreeInput> candidates, ArrayList<String> classname   s, boolean verbose){
//        if(verbose) System.out.println("Components found: ");       
//        Iterator it = candidates.iterator();
//        while(it.hasNext()){
//            Object next = it.next();
//            String cls = next.getClass().getCanonicalName();
//            if(verbose) System.out.println(cls);
//            if(classnames.contains(cls)){
//                System.out.println("chosen: " + next.getClass().getCanonicalName());
//                ai = (AreeInput) next;
//                //return;
//            }
//        }
//    }

//    public void configByDesc(String content) {
//        try {
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(content.getBytes("utf-8"))));
//            
//            Node root = doc.getDocumentElement();
//            NodeList nodes = root.getChildNodes();
//            int nNodes = nodes.getLength();
//            for(int i = 0; i < nNodes; i++){
//                Node node = nodes.item(i);
//                if(node.getNodeName().equals("input"))
//                    aiNames.add(node.getTextContent());
//            }
//            
//            
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(AreeReferee.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//                Logger.getLogger(AreeReferee.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(AreeReferee.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    
}