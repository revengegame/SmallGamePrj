import java.awt.Canvas;

import javax.swing.JFrame;

import com.smallgameprj.ui.GameCanvas;



public class Program {

	public static void main(String[] args) {
		
		JFrame win = new JFrame();
		win.setVisible(true);
		win.setSize(700,500);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas canvas = GameCanvas.getInstance();//new GameCanvas();
		//Canvas canvas = new Canvas();
		win.add(canvas);
		//바로 반응하지 않는 이유는 기본포커스가 윈도우프레임워크이기 때문
		//포커스를 윈도우프레임워크가 아닌 캔버스한테 줘라
		canvas.requestFocus();
		//유효성검사 출력이 되었다는 것을 다시 검증
		win.validate();
		
		
	}
}
