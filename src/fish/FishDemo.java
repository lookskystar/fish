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
	 *  ������
	 *  ����ͼƬ���ڴ�ռ�
	 *  IOException ��һ���쳣
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
		 * �����������͡���С�����
		 * Font.BOLD: ����
		 */
		g.setFont(new Font("����",Font.ITALIC,24));
		/**
		 * ����������ɫ
		 */
		g.setColor(Color.cyan);
		/**
		 * drawString(Ҫ���������,x����,y����);
		 */
		g.drawString("���ĵ÷���:"+score, 50, 50);
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
			//������ܳ�ȥ�ˣ������ܻ���
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
		JFrame frame = new JFrame("��ʹ����");
		JFrame frame1 = new JFrame("��ʹ����2");
		//�Դ��ڽ���һЩ����
		//���ô�С  ��ȣ��߶� ��λ������
		frame.setSize(800,510);
		frame1.setSize(400,510);
		//�ô��ڴ�С���ܸı�
		frame.setResizable(false);
		//�رմ���ʱ��ͬʱ���˳�ϵͳ
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
			}//�������
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
