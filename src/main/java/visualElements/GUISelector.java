package visualElements;

import GUI.Sidebar;
import org.jfugue.player.Player;
import player.MXLParser;

/**
 * this class is a singleton class
 * that will be call during user select event.
 * when some vElement is selected.
 * GUI will call
 * setSElement(vElement) to highlight that vElement.
 * @author Kuimou Yu
 * */
public class GUISelector {
	private static GUISelector GUISelector = new GUISelector();
	private VElement vElement;
	private Sidebar sidebar;
	private GUISelector(){

	}

	public static GUISelector getInstance() {
		return GUISelector;
	}

	public void setSidebar(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	/**
	 * highlight notes for select states
	 * @param sElement note that you want to highlight during select states
	 *
	 * */
	public void setSElement(VElement sElement) {
		if (sElement instanceof VPage||sElement instanceof VLine){
			return;
		}
		if (sElement!=vElement){
			if (vElement!=null){
				if (vElement== PlayingSelector.getInstance().getSElement()){
					vElement.setHighLight(HighLight.SELECTED);
				}else {
					vElement.setHighLight(HighLight.NULL);
				}
			}
			vElement = sElement;
			sidebar.update(vElement);
			if (vElement instanceof VNoteHead){
				Player player = new Player();
				player.delayPlay(0, MXLParser.getSingleNote(((VNoteHead) vElement).getNote(),VConfig.getInstance().instrument));
			}
			if (vElement!=null){
				if (vElement.getConfigAbleList().size()>0){
					sidebar.openDrawer();
				}
				vElement.setHighLight(HighLight.SELECTED);
			}
		}else {
			if (sElement!=null){
				if (sElement== PlayingSelector.getInstance().getSElement()){
					sElement.setHighLight(HighLight.PLAY);
				}else {
					sElement.setHighLight(HighLight.NULL);
				}
			}
			vElement = null;
			sidebar.update(vElement);
		}
	}


	public VElement getSElement() {
		return vElement;
	}
}
