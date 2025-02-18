package visualElements;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class VTime extends VSign{
	public Text upper;
	public Text lower;
	public VTime(int beats,int value){
		upper = new Text(beats+"");
		lower = new Text(value+"");
		group.getChildren().add(upper);
		group.getChildren().add(lower);
	}

	@Override
	public void alignment(){
		List<Integer> staffDetail = VConfig.getInstance().getStaffDetail();
		double step = VConfig.getInstance().getGlobalConfig("Step");
		double start = staffDetail.get(0)*step;
		double end = staffDetail.get(staffDetail.size()-1)*step;
		upper.setFont(new Font((end-start)*1.25/2));
		lower.setFont(new Font((end-start)*1.25/2));
		upper.setLayoutY((end-start)/2);
		lower.setLayoutY(end);
		Bounds upperBoundsInLocal = upper.getBoundsInLocal();
		Bounds lowerBoundsInLocal = lower.getBoundsInLocal();
		W = Math.max(upperBoundsInLocal.getWidth(),lowerBoundsInLocal.getWidth());
		setHighLight(highLight);
	}

	@Override
	public void setHighLight(HighLight states) {
		Color color = null;
		highLight = states;
		switch (states){
			case NULL -> color = VConfig.getInstance().getDefaultColor();
			case PLAY -> color = VConfig.getInstance().getPlayColor();
			case SELECTED -> color = VConfig.getInstance().getHighLightColor();
		}
		upper.setFill(color);
		lower.setFill(color);
	}
}
