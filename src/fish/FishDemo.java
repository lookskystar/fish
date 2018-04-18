package fish;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Pool extends JPanel{
	int x, y;
	BufferedImage background;
	Fish[] fishes;
    Fish nemo;
    Net net;
    int score =0;
	/**
	 *  构造器
	 *  加载图片到内存空间
	 *  IOException 是一个异常
	 */
	public Pool() throws IOException{
		int[] arry = new int[]{0,0,0};
		int[] a= {0,0,0};
		net = new Net();
		background = ImageIO.read(new File("bg.jpg"));
		fishes = new Fish[22];
		for(int i = 0;i<fishes.length ;i++){
			fishes[i] = new Fish("fish0"+(i%9+1));
		}
		for(int j = 0;j<fishes.length%9;j++){
			if(j%2==0){
				fishes[j] = new Fish("fish13");
			}
			fishes[j] = new Fish("fish14");
		}
		
		
		
	}
	public void action() throws InterruptedException, IOException{
		

			for(int i =0;i<fishes.length;i++){
				fishes[i].start();
				//fish.image = new Fish()
			}
			MouseAdapter m = new MouseAdapter() {
				public void mouseEntered(MouseEvent e){
					net.show = true;
				}
				public void mouseExited(MouseEvent e){
					net.show = false;
				}
				public void mouseMoved(MouseEvent e){
					//long time = e.getWhen();
					 net.x = e.getX();
					net.y = e.getY();
					//Object o = e.getSource();
				}
				public void mousePressed(MouseEvent e){
					catchFish();
				}
			};
			this.addMouseListener(m);
			this.addMouseMotionListener(m);
			while(true){
			repaint();
			Thread.sleep(500);
		}
	}
	public  void catchFish() {
		for(int i =0 ;i<fishes.length;i++){
			Fish fish = fishes[i];
		if(fish.contains(net.x,net.y)){
			System.out.println("cathc");
			fish.getOut();
			score +=fish.width/10; 
		}
		}
	}
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		for(int i =0;i<fishes.length;i++){
			Fish fish = fishes[i];
				g.drawImage(fish.image, fish.x, fish.y, null);
						
			}
		//paint net
		if(net.show){
			g.drawImage(net.image, net.x-net.width/2, net.y-net.height/2,null );
		}
		/**
		 * 设置字体类型、大小、风格
		 * Font.BOLD: 常量
		 */
		g.setFont(new Font("宋体",Font.ITALIC,24));
		/**
		 * 给画笔上颜色
		 */
		g.setColor(Color.cyan);
		/**
		 * drawString(要输出的内容,x坐标,y坐标);
		 */
		g.drawString("您的得分是:"+score, 50, 50);
	}
}
class Fish extends Thread{
	int x;
	int y;
	int width;
	int height;
	BufferedImage image;
	BufferedImage[] images;
	int step;
	int index;
	public Fish(){}
	public boolean contains(int netx, int nety) {
		int dx = netx-x;
		int dy = nety-y;
		
		return dx>0&&dx<width&&dy>0&&dy<height;
	}
	public Fish(String perfix) throws IOException{
		images = new BufferedImage[10];
		for(int i = 0;i<images.length-1 ;i++){
			images[i] = ImageIO.read(new File(perfix+"_0"+(i+1)+".png"));
		}
		images[images.length-1] = ImageIO.read(new File(perfix+"_10.png"));
		image = images[0];
		width = image.getWidth();
		height = image.getHeight();
		x = new Random().nextInt(800-image.getWidth());
		y = new Random().nextInt(510-image.getHeight());
		step = new Random().nextInt(3)+1;
	}
	public void run(){
		while(true){
			x -=step;//
			//如果鱼跑出去了，就再跑回来
			if(x<-width){
				getOut();
				
			}
			index++;
			image = images[index%images.length];
			try {
				Thread.sleep(1000/24);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void getOut() {
		x = 800;
		y = new Random().nextInt(500-height);
		
	}
}
public  class FishDemo{
	public static void main(String[] args) throws IOException, InterruptedException {
		JFrame frame = new JFrame("天使鱼塘");
		JFrame frame1 = new JFrame("天使鱼塘2");
		//对窗口进行一些设置
		//设置大小  宽度，高度 单位是像素
		frame.setSize(800,510);
		frame1.setSize(400,510);
		//让窗口大小不能改变
		frame.setResizable(false);
		//关闭窗口时，同时，退出系统
		frame.setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);
		frame1.setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);
		final Pool pool = new Pool();
		System.out.println(1+2+"java"+3+4);
		Pool pool1 = new Pool();
		frame.add(pool);
		frame1.add(pool1);
		frame.setVisible(true);
		
		new Thread(){
			public void run(){
			try {
				pool.action();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//启动鱼池
			}
		}.start();
		frame1.setVisible(true);
		pool1.action();
	}
}
class Net extends Thread{
	int x;
	int y;
	int width;
	int height;
	BufferedImage image;
	boolean show;
	public Net() throws IOException{
		image = ImageIO.read(new File("net09.png"));
		width = image.getWidth();
		height = image.getHeight();
		show = false;
		x = 0;
		y = 0;
	}
	
}
