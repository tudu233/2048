package com.example.game2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity implements
		GestureDetector.OnGestureListener {

	final int[] backgrounds = new int[] { R.drawable.photo0, R.drawable.photo2,
			R.drawable.photo4, R.drawable.photo8, R.drawable.photo16,
			R.drawable.photo32, R.drawable.photo64, R.drawable.photo128,
			R.drawable.photo256, R.drawable.photo512, R.drawable.photo1024,
			R.drawable.photo2048, R.drawable.photo4096,R.drawable.photo8192,
			R.drawable.photo16384};
	final int[][] cardsId = new int[][] {
			{ R.id.card00, R.id.card01, R.id.card02, R.id.card03 },
			{ R.id.card10, R.id.card11, R.id.card12, R.id.card13 },
			{ R.id.card20, R.id.card21, R.id.card22, R.id.card23 },
			{ R.id.card30, R.id.card31, R.id.card32, R.id.card33 } };
	private int[][] matrix = new int[4][4];
	int score = 0;
	boolean currentState = true;
	Button newGame = null;
	TextView scoreText = null;
	GestureDetector detector = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialization();
		setOnListener();
		gameStart();
	}

	// 初始化操作
	private void initialization() {

		setContentView(R.layout.activity_main);

		// 初始化矩阵
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				matrix[i][j] = 0;

		// 初始化重新开始按钮， 成绩和最好成绩TextView， 手势监听器detector
		newGame = (Button) findViewById(R.id.newgame);
		scoreText = (TextView) findViewById(R.id.scoretext);
		detector = new GestureDetector(MainActivity.this, this);

		// 初始化成绩
		scoreText.setText(score + "");
	}

	// 左滑事件处理
	private void leftShift() {
		// temp[4][4]数组来记录把原来matrix[4][4]数组中所有数推向左边后的结果
		int[][] temp = new int[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		int nums = 0;
		// Equal zero numbers等于0的数的总数，为后面随机产生一张卡片做准备
		int ezn = 16;
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (matrix[i][j] != 0) {
					temp[i][nums] = matrix[i][j];
					++nums;
				}
			}
			nums = 0;
		}

		// 对temp数组执行必要的相加的逻辑
		for (int i = 0; i < 4; ++i) {
			if ((temp[i][0] != 0) && (temp[i][0] == temp[i][1])) {
				temp[i][0] = temp[i][0] << 1;
				score += temp[i][0];
				temp[i][1] = 0;
				if ((temp[i][2] != 0) && (temp[i][2] == temp[i][3])) {
					temp[i][2] = temp[i][2] << 1;
					score += temp[i][2];
					temp[i][3] = 0;
				}
			} else {
				if ((temp[i][1] != 0) && (temp[i][1] == temp[i][2])) {
					temp[i][1] = temp[i][1] << 1;
					score += temp[i][1];
					temp[i][2] = 0;
				} else {
					if ((temp[i][2] != 0) && (temp[i][2] == temp[i][3])) {
						temp[i][2] = temp[i][2] << 1;
						score += temp[i][2];
						temp[i][3] = 0;
					}
				}
			}
		}

		// 将matrix数组初始化
		for (int i = 0; i < 4; ++i)
			for (int j = 0; j < 4; ++j)
				matrix[i][j] = 0;

		// 把temp数组重新赋值给matrix数组，并且把temp数组全部元素压到最左边
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (temp[i][j] != 0) {
					matrix[i][nums] = temp[i][j];
					++nums;
					--ezn;
				}
			}
			nums = 0;
		}

		// 当处理后仍然全部不为0表示游戏结束
		if (ezn == 0) {
			currentState = false;
		} else {
			// 随机增加一张卡片
			Random rand = new Random();
			int where = rand.nextInt(ezn) + 1;
			for (int count = 0, i = 0; i < 4; ++i)
				for (int j = 0; j < 4; ++j) {
					if (matrix[i][j] == 0)
						count++;
					if (count == where) {
						matrix[i][j] = getCard();
						return;

					}
				}
		}
	}

	// 右滑事件处理
	private void rightShift() {

		// temp[4][4]数组来记录把原来matrix[4][4]数组中所有数推向右边后的结果
		int[][] temp = new int[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		int nums = 3;

		// Equal zero numbers等于0的数的总数，为后面随机产生一张卡片做准备
		int ezn = 16;
		for (int i = 0; i < 4; ++i) {
			for (int j = 3; j >= 0; --j) {
				if (matrix[i][j] != 0) {
					temp[i][nums] = matrix[i][j];
					--nums;
				}
			}
			nums = 3;
		}

		// 对temp数组执行必要的相加的逻辑
		for (int i = 0; i < 4; ++i) {
			if ((temp[i][3] != 0) && (temp[i][3] == temp[i][2])) {
				temp[i][3] = temp[i][3] << 1;
				score += temp[i][3];
				temp[i][2] = 0;
				if ((temp[i][1] != 0) && (temp[i][1] == temp[i][0])) {
					temp[i][1] = temp[i][1] << 1;
					score += temp[i][1];
					temp[i][0] = 0;
				}
			} else {
				if ((temp[i][2] != 0) && (temp[i][2] == temp[i][1])) {
					temp[i][2] = temp[i][2] << 1;
					score += temp[i][2];
					temp[i][1] = 0;
				} else {
					if ((temp[i][1] != 0) && (temp[i][1] == temp[i][0])) {
						temp[i][1] = temp[i][1] << 1;
						score += temp[i][1];
						temp[i][0] = 0;
					}
				}
			}
		}

		// 将matrix数组初始化
		for (int i = 0; i < 4; ++i)
			for (int j = 0; j < 4; ++j)
				matrix[i][j] = 0;

		// 把temp数组重新赋值给matrix数组，并且把temp数组全部元素压到最左边
		for (int i = 0; i < 4; ++i) {
			for (int j = 3; j >= 0; --j) {
				if (temp[i][j] != 0) {
					matrix[i][nums] = temp[i][j];
					--nums;
					--ezn;
				}
			}
			nums = 3;
		}

		// 当处理后仍然全部不为0表示游戏结束
		if (ezn == 0) {
			currentState = false;
		} else {
			// 随机增加一张卡片
			Random rand = new Random();
			int where = rand.nextInt(ezn) + 1;
			for (int count = 0, i = 0; i < 4; ++i)
				for (int j = 0; j < 4; ++j) {
					if (matrix[i][j] == 0)
						count++;
					if (count == where) {
						matrix[i][j] = getCard();
						return;
					}
				}
		}

	}

	// 上滑事件处理
	private void upShift() {
		// temp[4][4]数组来记录把原来matrix[4][4]数组中所有数推向上边后的结果
		int[][] temp = new int[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		int nums = 0;
		// Equal zero numbers等于0的数的总数，为后面随机产生一张卡片做准备
		int ezn = 16;
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (matrix[j][i] != 0) {
					temp[nums][i] = matrix[j][i];
					++nums;
				}
			}
			nums = 0;
		}

		// 对temp数组执行必要的相加的逻辑
		for (int i = 0; i < 4; ++i) {
			if ((temp[0][i] != 0) && (temp[0][i] == temp[1][i])) {
				temp[0][i] = temp[0][i] << 1;
				score += temp[0][i];
				temp[1][i] = 0;
				if ((temp[2][i] != 0) && (temp[2][i] == temp[3][i])) {
					temp[2][i] = temp[2][i] << 1;
					score += temp[2][i];
					temp[3][i] = 0;
				}
			} else {
				if ((temp[1][i] != 0) && (temp[1][i] == temp[2][i])) {
					temp[1][i] = temp[1][i] << 1;
					score += temp[1][i];
					temp[2][i] = 0;
				} else {
					if ((temp[2][i] != 0) && (temp[2][i] == temp[3][i])) {
						temp[2][i] = temp[2][i] << 1;
						score += temp[2][i];
						temp[3][i] = 0;
					}
				}
			}
		}

		// 将matrix数组初始化
		for (int i = 0; i < 4; ++i)
			for (int j = 0; j < 4; ++j)
				matrix[i][j] = 0;

		// 把temp数组重新赋值给matrix数组，并且把temp数组全部元素压到最上边
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (temp[j][i] != 0) {
					matrix[nums][i] = temp[j][i];
					++nums;
					--ezn;
				}
			}
			nums = 0;
		}

		// 当处理后仍然全部不为0表示游戏结束
		if (ezn == 0) {
			currentState = false;
		} else {
			// 随机增加一张卡片
			Random rand = new Random();
			int where = rand.nextInt(ezn) + 1;
			for (int count = 0, i = 0; i < 4; ++i)
				for (int j = 0; j < 4; ++j) {
					if (matrix[i][j] == 0)
						count++;
					if (count == where) {
						matrix[i][j] = getCard();
						return;

					}
				}
		}
	}

	// 下滑事件处理
	private void downShift() {

		// temp[4][4]数组来记录把原来matrix[4][4]数组中所有数推向下边后的结果
		int[][] temp = new int[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		int nums = 3;
		// Equal zero numbers等于0的数的总数，为后面随机产生一张卡片做准备
		int ezn = 16;
		for (int i = 0; i < 4; ++i) {
			for (int j = 3; j >= 0; --j) {
				if (matrix[j][i] != 0) {
					temp[nums][i] = matrix[j][i];
					--nums;
				}
			}
			nums = 3;
		}

		// 对temp数组执行必要的相加的逻辑
		for (int i = 0; i < 4; ++i) {
			if ((temp[3][i] != 0) && (temp[3][i] == temp[2][i])) {
				temp[3][i] = temp[3][i] << 1;
				score += temp[3][i];
				temp[2][i] = 0;
				if ((temp[1][i] != 0) && (temp[1][i] == temp[0][i])) {
					temp[1][i] = temp[1][i] << 1;
					score += temp[1][i];
					temp[0][i] = 0;
				}
			} else {
				if ((temp[2][i] != 0) && (temp[2][i] == temp[1][i])) {
					temp[2][i] = temp[2][i] << 1;
					score += temp[2][i];
					temp[1][i] = 0;
				} else {
					if ((temp[1][i] != 0) && (temp[1][i] == temp[0][i])) {
						temp[1][i] = temp[1][i] << 1;
						score += temp[1][i];
						temp[0][i] = 0;
					}
				}
			}
		}

		// 将matrix数组初始化
		for (int i = 0; i < 4; ++i)
			for (int j = 0; j < 4; ++j)
				matrix[i][j] = 0;

		// 把temp数组重新赋值给matrix数组，并且把temp数组全部元素压到最下边
		for (int i = 0; i < 4; ++i) {
			for (int j = 3; j >= 0; --j) {
				if (temp[j][i] != 0) {
					matrix[nums][i] = temp[j][i];
					--nums;
					--ezn;
				}
			}
			nums = 3;
		}

		// 当处理后仍然全部不为0表示游戏结束
		if (ezn == 0) {
			currentState = false;
		} else {
			// 随机增加一张卡片
			Random rand = new Random();
			int where = rand.nextInt(ezn) + 1;
			for (int count = 0, i = 0; i < 4; ++i)
				for (int j = 0; j < 4; ++j) {
					if (matrix[i][j] == 0)
						count++;
					if (count == where) {
						matrix[i][j] = getCard();
						return;

					}
				}
		}
	}

	// 为重新开始按钮设置监听
	private void setOnListener() {
		newGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				for(int i = 0; i < 4; i++)
					for(int j = 0; j < 4; j++)
						matrix[i][j] = 0;
				showMatrix();
				score = 0;
				setScore(0);
				gameStart();
			}
		});
	}

	// 设置成绩时调用的函数
	private void setScore(int score) {
		scoreText.setText(score + "");
	}

	// 随机生成卡片，生成卡片2比卡片4的比例为4:1
	private int getCard() {
		Random rand = new Random();
		int num = rand.nextInt(5);
		if (num < 4)
			return 2;
		else
			return 4;
	}

	// 开始游戏,开始时只有两张卡片
	private void gameStart() {
		Random rand = new Random();
		int num = 0;
		while (num < 2) {
			int row = rand.nextInt(4);
			int column = rand.nextInt(4);
			if (matrix[row][column] == 0) {
				TextView card = (TextView) findViewById(cardsId[row][column]);
				int cardNum = getCard();
				card.setBackgroundResource(backgrounds[cardNum / 2]);
				card.setText(cardNum + "");
				matrix[row][column] = cardNum;
				num++;
			}
		}
		showMatrix();
	}

	private void showMatrix() {
		for (int i = 0; i < 4; ++i)
			for (int j = 0; j < 4; ++j) {
				TextView card = (TextView) findViewById(cardsId[i][j]);
				// 得到数字对应的背景图片
				if (matrix[i][j] == 0) {
					card.setBackgroundResource(backgrounds[0]);
					card.setText("");
				} else {
					int backgroundNum = (int) (Math.log(matrix[i][j]) / Math
							.log(2));
					card.setBackgroundResource(backgrounds[backgroundNum]);
					card.setText(matrix[i][j] + "");
				}
			}
	}

	private void check() {
		if (currentState)
			return;
		else {
			new AlertDialog.Builder(MainActivity.this)
					.setPositiveButton("再来一次", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							for(int i = 0; i < 4; i++)
								for(int j = 0; j < 4; j++)
									matrix[i][j] = 0;
							showMatrix();
							score = 0;
							setScore(0);
							gameStart();
						}
					})
					.setNegativeButton("取消", null)
					.show();
			currentState = true;
		}
	}


	@Override
	public boolean onDown(MotionEvent e) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
		if (e1.getX() - e2.getX() > 50) {
			leftShift();
			showMatrix();
			setScore(score);
			check();
		} else if (e2.getX() - e1.getX() > 50) {
			rightShift();
			showMatrix();
			setScore(score);
			check();
		} else if (e1.getY() - e2.getY() > 40) {
			upShift();
			showMatrix();
			setScore(score);
			check();
		} else if(e2.getY() - e1.getY() > 40){
			downShift();
			showMatrix();
			setScore(score);
			check();
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {

		boolean state = detector.onTouchEvent(e);
		if (state)
			return true;
		else
			return super.dispatchTouchEvent(e);
	}

}
