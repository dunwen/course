package cn.cqut.edu.courseview;

import com.example.temp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Course_info extends Activity {

	private TextView tv_courseName;
	private TextView tv_teacherName;
	private TextView tv_location;
	private TextView tv_zhouci;
	private TextView tv_jieci;
	private TextView tv_timeofday;
	
	private TextView tv_head_whichteam;
	private TextView tv_head_whichweek;
	private TextView tv_head_whichDayOfWeek;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);
		init();
	}

	private void init() {
		tv_courseName = (TextView) findViewById(R.id.info_courseName);
		tv_jieci = (TextView) findViewById(R.id.info_jieci);
		tv_location = (TextView) findViewById(R.id.info_location);
		tv_teacherName = (TextView) findViewById(R.id.info_teacherName);
		tv_timeofday = (TextView) findViewById(R.id.info_timeofday);
		tv_zhouci = (TextView) findViewById(R.id.info_coursezhouci);
		
		tv_head_whichteam = (TextView) findViewById(R.id.time_team);
		tv_head_whichweek = (TextView) findViewById(R.id.time_which);
		tv_head_whichDayOfWeek =(TextView) findViewById(R.id.time_weekofday);
		
		getActionBar().hide();
		((ImageView)findViewById(R.id.iv_xiangxia)).setVisibility(View.GONE);
		
		getAndSetData();
		
		((ImageView)findViewById(R.id.iv_back)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Course_info.this.finish();
			}
		});
	}

	private void getAndSetData() {
		Bundle bundle = getIntent().getExtras();
		if(bundle.getString("courseName")!=null){
			tv_courseName.setText(bundle.getString("courseName"));  //懒得改，就让它获取两次吧
		}
		if(bundle.getString("teacherName")!=null){
			tv_teacherName.setText(bundle.getString("teacherName"));  //懒得改，就让它获取两次吧
		}
		if(bundle.getString("location")!=null){
			tv_location.setText(bundle.getString("location"));  //懒得改，就让它获取两次吧
		}
		if(bundle.getString("zhouci")!=null){
			tv_zhouci.setText(bundle.getString("zhouci"));  //懒得改，就让它获取两次吧
		}
		if(bundle.getString("jieci")!=null){
			tv_jieci.setText(bundle.getString("jieci"));  //懒得改，就让它获取两次吧
		}
		if(bundle.getString("timeOfDay")!=null){
			tv_timeofday.setText(bundle.getString("timeOfDay"));  //懒得改，就让它获取两次吧
		}
	
		
		
		
		if(bundle.getString("whichTeam")!=null){
			tv_head_whichteam.setText(bundle.getString("whichTeam"));  //懒得改，就让它获取两次吧
		}
		if(bundle.getString("whichWeek")!=null){
			tv_head_whichweek.setText(bundle.getString("whichWeek"));  //懒得改，就让它获取两次吧
		}
		if(bundle.getString("whichDayOfWeek")!=null){
			tv_head_whichDayOfWeek.setText(bundle.getString("whichDayOfWeek"));  //懒得改，就让它获取两次吧
		}
		
	}


}
