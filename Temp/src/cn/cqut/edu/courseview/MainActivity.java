package cn.cqut.edu.courseview;

import java.util.HashMap;
import java.util.Map;

import com.example.temp.R;

import android.widget.LinearLayout.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * 基本思路就是在布局设置vertical的linearlayout，然后通过for循环向其内容添加课程格子（addview）
 * 
 * @author dun
 * */
public class MainActivity extends Activity implements OnClickListener{
	
	/**
	 * 列数，就是打横的那排的数目
	 * */
	final int COURSE_CEIL_ROW = 8;
	
	/**
	 * 行数，就是打竖的那排的数目
	 * */
	final int COURSE_CEIL_COL = 11;
	
	/**
	 * 选择周数的popupwindow显示的最大数目
	 * */
	final int MAX_POPWINDOW_SHOW = 5;
	
	/**
	 * 设置普通课程格子的高度
	 * */
	final int COMMON_HEIGHT = 70;
	
	/**
	 * 第一横排的课程格子的高度
	 * */
	final int HEAD_HEIGHT = 40;
	
	/**
	 * 是否在现实popupwindow
	 * */
	private boolean ispopwindowshowing = false; 
	
	private TextView tv_timewhich;
	private LinearLayout[] lllist = new LinearLayout[COURSE_CEIL_ROW];
	private LinearLayout ll_heng; 
	private LinearLayout ll_choosecourse;
	private PopupWindow pw;
	private ListView lv_popupwindow;

	
	/**
	 * 我偷懒了没有封装课程类，仅仅用了字符串代表课程
	 * */
	private String[][] course = new String[COURSE_CEIL_ROW][COURSE_CEIL_COL];

	
	
	
	/**
	 * 当前选择的popupwindow下标
	 * */
	private int current_select_popwindiwceil = 0;
	

	int headwidth;
	int daywidth;
	
	int[] llId ={R.id.head,R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday,R.id.saturday,R.id.sunday}; 
	
	String[] dayOfWeek = {"周一","周二","周三","周四","周五","周六","周日"};
	
	/**
	 * popupwindow 显示的周数，当前设置20周
	 * */
	String[] popupwindow_cell = new String[20];
	
	
	/**
	 * 这是课程的背景颜色，懒得p图，也可以用xml的shape代替（不知道用多了图片背景会不会内存溢出~）
	 * */
	int[] color = {R.drawable.background,R.drawable.background2,R.drawable.background3,R.drawable.background4,R.drawable.background5};
	
	/**
	 * 存贮课程跟背景的对应关系（相同课程名背景颜色相同）
	 * */
	Map<String,Integer> courseMap = new HashMap<String,Integer>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init() {
		getActionBar().hide(); 
		
		tv_timewhich = (TextView) findViewById(R.id.time_which);
		
		
		
		ll_heng = (LinearLayout) findViewById(R.id.head_heng);
		ll_choosecourse = (LinearLayout) findViewById(R.id.ll_choosecourse);
		ll_choosecourse.setOnClickListener(this);
		
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

		headwidth = wm.getDefaultDisplay().getWidth() / 16;
		daywidth = (wm.getDefaultDisplay().getWidth() - headwidth) / (dayOfWeek.length);
		
		for(int i = 0 ; i < COURSE_CEIL_ROW;i++) {
			for(int j = 0 ; j < COURSE_CEIL_COL;j++) {
				course[i][j] = "";
			}
		}
		
		for(int i = 0 ;i<20;i++){
			popupwindow_cell[i] = "第" +(int)(i+1)+ "周" ;
		}
		
		tv_timewhich.setText(popupwindow_cell[current_select_popwindiwceil]);
		
		course[0][2] = "数据库原理及应用@3教0404";
		course[0][3] = "数据库原理及应用@3教0404";
		
		course[0][6] = "Linux基础与应用@4教0312";
		course[0][7] = "Linux基础与应用@4教0312";
		
		course[1][0] = "概率论与数理统计【理工】@6教0213";
		course[1][1] = "概率论与数理统计【理工】@6教0213";

		
		course[3][0] = "概率论与数理统计【理工】@6教0213";
		course[3][1] = "概率论与数理统计【理工】@6教0213";
		
		course[1][2] = "马克思主义基本原理概论@3教0409";
		course[1][3] = "马克思主义基本原理概论@3教0409";
		
		course[5][2] = "马克思主义基本原理概论@3教0409";
		course[5][3] = "马克思主义基本原理概论@3教0409";

		
		
		for(int i = 0; i < COURSE_CEIL_ROW; i++) {
			lllist[i] = (LinearLayout) findViewById(llId[i]);
		}
		
		initpopupwindow();
		initView();
		
		

		
		
		
		
	}

	private void initpopupwindow() {
		
		LayoutInflater lf = LayoutInflater.from(this);
		View view = lf.inflate(R.layout.popupwindow_view,null);
		lv_popupwindow = (ListView) view.findViewById(R.id.popupwindow_lv);
		pw = new PopupWindow(view);
		
		//这里要重写一个自定义adapter
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,popupwindow_cell);
		
		
		lv_popupwindow.setAdapter(aa);
		lv_popupwindow.measure(0,0);
		pw.setHeight(lv_popupwindow.getMeasuredHeight()*(popupwindow_cell.length>=MAX_POPWINDOW_SHOW? MAX_POPWINDOW_SHOW:popupwindow_cell.length));
		pw.setWidth(lv_popupwindow.getMeasuredWidth());
		pw.setFocusable(true);
		pw.setOutsideTouchable(true);
		pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindowbackground));
		pw.setOutsideTouchable(true);
		
		lv_popupwindow.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				
				pw.dismiss();
				ispopwindowshowing = false;
				current_select_popwindiwceil = position;
				tv_timewhich.setText(popupwindow_cell[position]);
				
			}
		});
	}

	private void initView() {
		for(int c = 0; c < COURSE_CEIL_ROW; c++) { //c 就是 column
			for(int r = 0 ; r < COURSE_CEIL_COL;) { //r 就是 row ，列
				TextView tv = new TextView(this);
				int length = 0;
				LayoutParams lp;
				if(c == 0){ 
					if(r == 0) { //第一行第一列的特殊格子
						lp = new LayoutParams(headwidth, HEAD_HEIGHT);
						tv.setText("");
						lp.setMargins(0,2,1,0);
						tv.setLayoutParams(lp);
						r++;
						tv.setGravity(Gravity.CENTER);
						ll_heng.addView(tv);
						tv.setBackgroundColor(getResources().getColor(R.color.color_class_weekday));
						continue;
					}
					else { //第一列的其它格子
						lp = new LayoutParams(headwidth, COMMON_HEIGHT);
						tv.setText(r + "");
					}
					
					lp.setMargins(0,2,1,0);
					tv.setLayoutParams(lp);
					
					tv.setBackgroundColor(getResources().getColor(R.color.background));
					tv.setGravity(Gravity.CENTER);
					lllist[c].addView(tv);
				}
				
				
				//第一行的格子，就是显示周x的哪一行
				if(r == 0 && c!=0){
					lp = new LayoutParams(daywidth, HEAD_HEIGHT);
					lp.setMargins(0,2,1,0);
					tv.setLayoutParams(lp);
					tv.setBackgroundColor(getResources().getColor(R.color.color_class_weekday));
					tv.setGravity(Gravity.CENTER);
					tv.setText(dayOfWeek[c-1]);
					ll_heng.addView(tv);
				}
				
				//普通课程格子
				if(r != 0 && c != 0){
					
					int q = 1 ;
	//					if(!course[c][r].equals("")&&c<8&&r<12)
	//					System.out.println("c"+c+" r"+r+course[c][r]);
					
					//判断课程格子是否有内容，根据数组是否为空，若不为空，判断该课程的长度
					while(true && !course[c-1][r-1].equals("")&&c<8&&r<12){
						if(courseMap.get(course[c-1][r-1])==null)
							courseMap.put(course[c-1][r-1],color[courseMap.size()]);
						
						length++;
						if(!course[c-1][r+q-1].equals(course[c-1][r-1])){
							break;
						}
						q++;
						
					}
					if(length!=0){
						tv.setAlpha(0.9f);
						
						lp = new LayoutParams(daywidth,COMMON_HEIGHT*length+(int)(2*(length-1)));//要加回多的marin 2dp
						
						Drawable drawable = getResources().getDrawable(courseMap.get(course[c-1][r-1]));
	//						if(courseMap.size()!=0&&courseMap.get(course[c-1][r-1])!=null)
							
							
							tv.setBackground(drawable);
							final String courseName = course[c-1][r-1];
							final int currentC = c;
							
							//假若有内容，设置点击事件
							tv.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View view) {
									Intent intent = new Intent(MainActivity.this,Course_info.class);
									intent.putExtra("courseName",courseName);
									intent.putExtra("teacherName","");
									intent.putExtra("location","");
									intent.putExtra("zhouci","");
									intent.putExtra("jieci","");
									intent.putExtra("timeOfDay","");
									intent.putExtra("whichTeam",((TextView)findViewById(R.id.time_team)).getText());
									intent.putExtra("whichWeek",popupwindow_cell[current_select_popwindiwceil]);
									
									
									switch(currentC){
										case 1 : intent.putExtra("whichDayOfWeek","周一");break;
										case 2 : intent.putExtra("whichDayOfWeek","周二");break;
										case 3 : intent.putExtra("whichDayOfWeek","周三");break;
										case 4 : intent.putExtra("whichDayOfWeek","周四");break;
										case 5 : intent.putExtra("whichDayOfWeek","周五");break;
										case 6 : intent.putExtra("whichDayOfWeek","周六");break;
										case 7 : intent.putExtra("whichDayOfWeek","周日");break;
									}
									
									startActivity(intent);
									
								}
							});
							
							
							drawable = null;
					}
					else{ //空白的课程格子
						
						lp = new LayoutParams(daywidth,COMMON_HEIGHT);
						tv.setBackgroundColor(getResources().getColor(R.color.color_class_course));
					}
						
					lp.setMargins(0,2,1,0);
					tv.setLayoutParams(lp);
					tv.setGravity(Gravity.CENTER);
					tv.setText(course[c-1][r-1]);
					
					lllist[c].addView(tv);				
				}
				
				
				if(length!=0)
					r+=length;
				else
					r++;
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_choosecourse:
			
			popwindowshow();
			
			break;

		default:
			break;
		}
		
	}

	
	/**
	 * 显示popupwindow
	 * */
	private void popwindowshow() {
		if(ispopwindowshowing){
			pw.dismiss();
			ispopwindowshowing = false;
		}else{
			
			tv_timewhich.measure(0,0);
			ImageView iv = (ImageView) findViewById(R.id.iv_xiangxia);

			//下面4行是测量显示第x周和popupwindow的位置
			iv.measure(0,0);
			int ivwidth = iv.getMeasuredWidth();
			int offset = (int)((tv_timewhich.getMeasuredWidth()+ivwidth - lv_popupwindow.getMeasuredWidth())/2);
			pw.showAsDropDown(tv_timewhich,offset,0);
			
			
			
			ispopwindowshowing = true;
		}
	}

}