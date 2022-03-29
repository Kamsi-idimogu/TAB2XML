package visualElements;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import visualizer.ImageResourceHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VNoteHead extends VElement implements VConfigAble{
	ImageView imageView = new ImageView();
	Text text = new Text();
	ImageResourceHandler imageResourceHandler = ImageResourceHandler.getInstance();
	List<VDot> dots = new ArrayList<>();
	Rectangle background = new Rectangle();
	HashMap<String,Double> config = VConfig.getInstance().getDefaultConfigMap("noteHead");
	int relative = 0;
	double step = VConfig.getInstance().getGlobalConfig().get("Step");
	public VNoteHead(String AssetName,int dots,int relative){
		this.relative = relative;
		imageView.setImage(new Image(imageResourceHandler.getImage(AssetName)));
		imageView.setFitHeight(config.get("defaultSize")*config.get("scale"));
		imageView.setFitWidth(config.get("defaultSize")*config.get("scale"));
		group.getChildren().add(imageView);
		initDots(dots);
	}
	public VNoteHead(int fret,int dots,int relative){
		this.relative = relative-2; // double-spaced for tab

		text.setText(fret+"");
		text.setFont(new Font(config.get("defaultSize")*config.get("scale")));
		Bounds bounds = text.getBoundsInLocal();
		background.setWidth(bounds.getWidth());
		background.setHeight(bounds.getHeight());
		background.setFill(Color.WHITESMOKE);
		background.setLayoutY(-bounds.getHeight());
		group.getChildren().add(background);
		group.getChildren().add(text);

		initDots(dots);
	}

	@Override
	public void setHighLight(boolean states) {

		if (states){
			// add color fiter into image view
			
		}
		for (VDot dot:dots){
			dot.setHighLight(states);
		}
	}


	public void initDots(int number){
		for (int i = 0; i < number; i++) {
			dots.add(new VDot());
		}
	}
	public void alignment(){
		step = VConfig.getInstance().getGlobalConfig().get("Step");
		group.setLayoutY(relative*step);
		W = config.get("defaultSize")*config.get("scale");
		if (dots.size()>0){
			for (int i = 0; i < dots.size(); i++) {
				dots.get(i).getShapeGroups().setLayoutX(W);
				W += dots.get(i).getW();
			}
		}

	}
	@Override
	public HashMap<String, Double> getConfigAbleList() {
		return null;
	}

	@Override
	public void updateConfig(String id, double value) {
		switch (id){
			case "offsetX":
				break;
			case "offsetY":
				break;
			case "scale":
				break;
			case "color":
				break;
		}
	}
}
