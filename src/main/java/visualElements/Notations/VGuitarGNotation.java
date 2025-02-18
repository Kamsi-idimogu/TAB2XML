package visualElements.Notations;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import visualElements.VConfig;
import visualElements.VUtility;

import java.util.List;
import java.util.Stack;

public class VGuitarGNotation extends VGNotation{

	public VGuitarGNotation(){
		initConfig();
	}
	public void initConfig(){
		initConfigElement("GuitarNotationStartHeight",20d,0d, VConfig.getInstance().getGlobalConfig("PageX"));
		initConfigElement("GuitarNotationEndHeight",24d,0d,VConfig.getInstance().getGlobalConfig("PageX"));
		initConfigElement("DrumNotationHeight",-6d,-50d,VConfig.getInstance().getGlobalConfig("PageX"),false);
		initConfigElement("notationGap",10d,0d,VConfig.getInstance().getGlobalConfig("PageX"));
		initConfigElement("thickness",5d,1d,VConfig.getInstance().getGlobalConfig("PageX"));
		initConfigElement("dotSize",2d,0d,VConfig.getInstance().getGlobalConfig("PageX"));
		initConfigElement("dotOffset",6d,0d,VConfig.getInstance().getGlobalConfig("PageX"));
	}
	@Override
	public void alignment(List<Double> HPosition, List<Double> VPosition){
		double step = VConfig.getInstance().getGlobalConfig("Step");
		double start = configMap.get("GuitarNotationStartHeight")*step;
		double end = configMap.get("GuitarNotationEndHeight")*step;
		double gap = configMap.get("notationGap");
		double dotOffset = configMap.get("dotOffset");
		double dotSize = configMap.get("dotSize");
		Stack<Line> lineStack = new Stack<>();
		int globalHLineNum = 0;
		int HlinePointer = 0;
		int dotPointer = 0;
		for (int i =0;i<notes.size();i++){
			int localHline = Math.max(0,(int)(Math.log(VUtility.NoteType2Integer(types.get(i)))/Math.log(2))-2);

			Vlines.get(i).setLayoutX(HPosition.get(i));
			if (types.get(i).equals("half")||types.get(i).equals("whole")){
				Vlines.get(i).setStartY(start+(end-start)/2);
			}else {
				Vlines.get(i).setStartY(start);
			}
			Vlines.get(i).setEndY(end);

			if (dots.get(i)>0){
				for (int k=0;k<dots.get(i);k++){
					Circle circle = circles.get(dotPointer);
					circle.setLayoutX(HPosition.get(i)+(k+1)*dotOffset);
					if (types.get(i).equals("half")||types.get(i).equals("whole")){
						circle.setLayoutY(start+(end-start)/2);
					}else {
						circle.setLayoutY(start);
					}
					circle.setRadius(dotSize);
					circle.setFill(Color.BLACK);
					dotPointer++;
				}
			}
			if (localHline>globalHLineNum){
				int diff = localHline - globalHLineNum;
				globalHLineNum = localHline;
				if (notes.size()==1){
					for (int j=0;j<diff;j++){
						ImageView image = imageView.get(j);
						image.setPreserveRatio(true);
						image.setFitWidth(step*2);
						Bounds bounds = imageView.get(j).getBoundsInLocal();
						double radio = bounds.getHeight()/bounds.getWidth();
						double offset = radio*step*1.5;
						imageView.get(j).setFitHeight(step*1.5*radio);
						imageView.get(j).setLayoutY(end+j*gap-offset);
						imageView.get(j).setScaleY(-1);
						imageView.get(j).setLayoutX(HPosition.get(i));
					}
				}else {
					for (int j=0;j<diff;j++){
						lineStack.push(Hlines.get(HlinePointer));
						double startX = 0;
						if (i>0){
							if (i<notes.size()-1){
								if (VUtility.NoteType2Integer(types.get(i))<=VUtility.NoteType2Integer(types.get(i+1))){
									startX = HPosition.get(i);
								}else {
									startX = (HPosition.get(i) + HPosition.get(i - 1)) / 2;
								}
							}else {
								startX = (HPosition.get(i) + HPosition.get(i - 1)) / 2;
							}
						}else {
							startX = HPosition.get(i);
						}
						Hlines.get(HlinePointer).setStartX(startX);
						Hlines.get(HlinePointer).setStrokeWidth(configMap.get("thickness"));
						Hlines.get(HlinePointer).setLayoutY(end-(lineStack.size()-1)*gap);
						HlinePointer++;
					}
				}
			}else if (localHline<globalHLineNum){
				int diff = globalHLineNum - localHline;
				globalHLineNum = localHline;
				for (int j=0;j<diff;j++){
					Line line = lineStack.pop();
					if (i==1) {
						line.setEndX((HPosition.get(i) + HPosition.get(i - 1)) / 2);
					}else {
						line.setEndX(HPosition.get(i-1));
					}
				}
			}
		}
		while (!lineStack.isEmpty()){
			Line line = lineStack.pop();
			line.setEndX(HPosition.get(HPosition.size()-1));
		}
		setHighLight(highLight);
	}
}
