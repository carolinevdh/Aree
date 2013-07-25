package be.cvandenhauwe.aree.experimentcomponents;
import be.cvandenhauwe.aree.configuration.AreeArguments;
import be.cvandenhauwe.aree.configuration.AreeComponentInterface;
public class ExperimentThreeComponent40 implements AreeComponentInterface {
private String time;
@Override
public Object process(AreeArguments runtimeArguments, Object obj) throws Exception {
return time;
}
@Override
public void setup(AreeArguments setupArguments) throws Exception {
time = setupArguments.getValue("time") + "";
}
}