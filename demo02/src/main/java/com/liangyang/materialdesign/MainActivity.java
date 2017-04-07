package com.liangyang.materialdesign;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    //加载数据源
    private DataInfo[] dataInfos = {
            new DataInfo("图片1", R.drawable.bg01, "近日，锤子手机又降价了。京东商城的锤子科技官方旗舰店对新机M1L进行了降价处理，最高降价幅度达到了700元。\n" +
                    "        对于锤粉来说，降价并不是新闻。，M1的“前辈”锤子T1和T2都经历过不同程度的降价。\n" +
                    "        锤子T1在2015年5月上市，罗永浩此前放出狠话，“如果T1低于2500，我是你孙子“而上市的时候不到半年后锤子T1就降价1000元。网友也戏称罗永浩为“孙永浩”。\n" +
                    "        T2降价的步骤差不多，半年内直降了700元。如今M1系列延续了以前的传统。\n" +
                    "        其实，锤子已经沉寂有一段时间了，上次锤子的大新闻还是CTO出走。在智能手机竞争日趋激烈，品牌日趋集中化的今天，锤子已经快被遗忘了。\n" +
                    "        不过，近期罗永浩又开始出来活动了，放风说要发布新品，而有自媒体透露新品是坚果二代，定价会在1000元到2000元区间。\n" +
                    "        罗永浩在其他方面也不是很消停，通过内容变现又大赚了一笔。\n" +
                    "        旧品降价，新品在即，罗永浩和锤子还有前途可言吗？"),
            new DataInfo("图片2", R.drawable.bg02, "从移动支付到理财代销、从消费信贷到金融云、从农村金融到个人征信，互联网巨头跨界撬动金融业务的选择，总是无法避免又可以理解的趋同——因为巨头的金融基础（牌照、专业人员沉淀）、母体资源禀赋大部分是相同，存异的是商业决策。\n" +
                    "        在前述几轮对标后，如今蚂蚁金服、腾讯理财通和京东金融又在同一个商业逻辑上狭路相逢——“开放平台”战略。他们都宣称只做技术方，为合作金融机构输出金融科技能力。"),
            new DataInfo("图片3", R.drawable.bg03, "从移动支付到理财代销、从消费信贷到金融云、从农村金融到个人征信，互联网巨头跨界撬动金融业务的选择，总是无法避免又可以理解的趋同——因为巨头的金融基础（牌照、专业人员沉淀）、母体资源禀赋大部分是相同，存异的是商业决策。\n" +
                    "        在前述几轮对标后，如今蚂蚁金服、腾讯理财通和京东金融又在同一个商业逻辑上狭路相逢——“开放平台”战略。他们都宣称只做技术方，为合作金融机构输出金融科技能力。"),
            new DataInfo("图片4", R.drawable.bg04, "监管层颁布《网络借贷资金存管业务指引》刚满一个月，已有不少银行进军网贷（P2P）存管队伍。其中，中小银行开始降低平台准入门槛，大型股份行则依旧维持高要求。\n" +
                    "        另据融360不完全统计，截至3月20日，上线网贷存管系统的银行共有194家，较2月23日（156家）增加了38家；而网贷平台中，已上线银行存管系统的平台总数，仅占2335家正常运营平台的8.31%，还不到总数的十分之一\n" +
                    "        “指引下来后，有不少平台在寻求对接的银行，但都卡在费用上。”一家网贷平台负责人向证券时报记者透露，“对于大平台来说费用是小事，所以签约和上线都快，但小平台比较难以承受”。"),
            new DataInfo("图片5", R.drawable.bg05, "北京时间3月23日消息，苹果已经收购Workflow，它开发的工具可以将多个App或者App的功能连接起来，形成一组指令，自动完成任务。但苹果具体收购价尚不清楚。\n" +
                    "        Workflow App是一个小团队开发的，当中包括前iPhone越狱者阿里·温斯坦（Ari Weinstein）。Workflow已经运营几年了，它与IFTTT服务有点相似，用户可以将一组动作组织起来，一次就能完成复杂的任务。在短短几年里，Workflow积累了大量用户，下载量也很大。\n" +
                    "        苹果不只获得了Workflow App，开发团队也会加入苹果公司。收购之后App会继续存在于App Store，今天晚些时候就会免费。"),
            new DataInfo("图片6", R.drawable.bg06, "北京时间3月23日早间消息，为了通过增加开支和内容吸引用户，增强微信的粘性，腾讯计划剥离电子书业务。\n" +
                    "        在周三发表了低于分析师预期的季度盈利后，腾讯周三表示，其电子书业务将在香港IPO。虽然腾讯上财季净利润飙升47%，达到105亿元人民币（约合15亿美元），但仍然落后于分析师110亿元人民币的预期。"),
            new DataInfo("图片7", R.drawable.bg07, "持续了数月的供应链欠款危机之后，一款画风奇特的双摄手机“谍照”终于将乐视手机拉回了发布会预热的正轨。近日，乐视手机官微“剧透”新品预热信息，并将卖点瞄准了高性能兼顾长续航。这是否意味着，此前深陷资金链旋涡的乐视手机已经摆脱了危机？" +
                    "南都记者日前从一家手机摄像头供应商处获悉，乐视手机已经和供应商恢复了合作，该集团董事长曾亲自出马但仍未将乐视欠款讨回，之所以最后又接受了乐视新的订单，“一方面可以借机消化库存，另一方面是帮乐视一把，让它活下去之后再要钱。”"),
            new DataInfo("图片8", R.drawable.bg08, "为独立建站者提供社交评论框服务的多说今天宣布将关闭服务。多说已经成为了目前国内份额最大的所谓“社交评论框”服务，但是这个行业第一并没有给它带来更多的收益和发展空间。\n" +
                    "        多说曾经的最大竞争对手是仅仅做了四个月的友言，它卖身给了给网页增加分享按钮的JiaThis，当时的估价大概在200万到500万元左右。仅仅做了四个月就卖出去的主要原因是，当时创始人已经认为这个市场空间很小很有限了，而且竞争对手很有可能在收购不成之后选择自己做一个。\n" +
                    "        同一时期，类似的服务还包括bShare，以及进行相关文章推荐的无觅等等。这些社交评论框和分享按钮，是顺应当时个人网站和博客增加的趋势，站长只需用一行代码就可以给自己的网站实现各种高大上的附加功能。"),
            new DataInfo("图片9", R.drawable.bg09, "北京时间3月23日消息，当你在山里行走，或者听到别人说某个地方开了一个新酒吧，你想去看看，此时谷歌地图（Google Maps）可以成为你的坚强后盾。地图的一些功能很实用，比如速度计、逐向导航、停车提示。从今天开始，谷歌地图又增加一个新功能：分享位置信息。\n" +
                    "        分享位置并没有什么新鲜的。Waze拥有位置分享功能，Glympse可以分享位置。谷歌自己开发的Latitudes程序也可以将实时位置分享给朋友，不过这款程序2013年“退役”了。谷歌地图有9500万用户，在谷歌所有的App中，它的用户量排名第二，仅次于YouTube，在所有App中排名第四。"),
            new DataInfo("图片10", R.drawable.bg10, "3月22日，据彭博社报道称，iPhone或在下一代产品中引入AR（增强现实）技术。同时，苹果将在四月推出新的应用“Clips”。借助这一应用，用户可以将特效、过滤器加入到拍摄的作品中。\n" +
                    "        这被视为苹果CEO库克进军AR技术领域的决心。此前，他曾多次公开表示看好AR技术，认为它会像一日三餐，成为生活的一部分。对于上述消息，苹果中国公司相关人士在接受21世纪经济报道记者采访时未置可否，也未透露更多消息。\n" +
                    "        】AR的市场前景显然不容忽视。研究机构Global Market Insights预测，到2024年，AR产品市场规模将增长80%，达到1650亿美元。市场上的手机玩家并非只有苹果。在全球范围内，支持谷歌Tango增强现实平台的手机，包括了联想Phab2 Pro和华硕ZenFone AR，华为也正计划开发一部支持Tango技术的智能手机。")};

    //数据源集合
    private List<DataInfo> dataInfoList = new ArrayList<>();

    private boolean isList = true;

    private DrawerLayout mDrawerLayout;
    private View nav_header_name;
    private View nav_header_email;
    private View nav_header_imageView;
    private FloatingActionButton floatActionBtn;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);//将Toolbar设置为Actionbar

        //初始化dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.def_head);
        builder.setTitle("提示");
        builder.setView(R.layout.dialog_view_item);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("查看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "你点击了查看按钮", Toast.LENGTH_SHORT).show();

            }
        });
        builder.create().show();

        //初始化抽屉控件
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //设置标题栏导航栏图标
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //设置NavigationView头部控件的点击事件
        View headerView = navigationView.getHeaderView(0);//获取头部控件
        nav_header_imageView = headerView.findViewById(R.id.nav_icon_image);
        nav_header_name = headerView.findViewById(R.id.user_name);
        nav_header_email = headerView.findViewById(R.id.email);
        nav_header_imageView.setOnClickListener(this);
        nav_header_name.setOnClickListener(this);
        nav_header_email.setOnClickListener(this);

        //设置NavigationView中的menu的默认选中状态栏
        navigationView.setCheckedItem(R.id.nav_menu_call);//也可以在XML文件中设置
        //设置NavigationView中的menu的点击监听事件
        navigationView.setNavigationItemSelectedListener(this);

        //初始化FloatingActionButton --> SnackBar
        floatActionBtn = (FloatingActionButton) findViewById(R.id.fab);
        floatActionBtn.setOnClickListener(this);

        //初始化数据源
        initData();

        //初始化RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //初始化适配器
        adapter = new RecyclerViewAdapter(MainActivity.this, dataInfoList);
        //绑定适配器
        recyclerView.setAdapter(adapter);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //刷新条颜色,setColorSchemeResources(int… args)：设置刷新时圆圈的颜色变化，为int数组
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light);
        //setSize(int size)：设置刷新时圆圈的大小，有DEFAULT和LARGE两个值，默认是DEFAULT
        mSwipeRefresh.setSize(SwipeRefreshLayout.LARGE);
        //setOnRefreshListener(OnRefreshListener listener)：设置刷新时回调监听事件，刷新时调用
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * 当刷新的时候进行回调
             */
            @Override
            public void onRefresh() {
                //在这里执行操作的更新等操作
                refreshData();
            }
        });

    }

    /**
     * 刷新数据
     */
    private void refreshData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //休眠2秒，模拟网络加载耗时操作
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        initData();
                        //刷新适配器
                        adapter.notifyDataSetChanged();
                        //setRefreshing(boolean refreshing)：设置是否继续正在刷新
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /**
     * 初始换数据源
     */
    private void initData() {

        dataInfoList.clear();
        for (int i = 0; i < 50; i++) {
            //随机放置
            Random random = new Random();
            int index = random.nextInt(dataInfos.length);
            dataInfoList.add(dataInfos[index]);
        }

//        for (int i = 0; i < dataInfos.length; i++) {
//            dataInfoList.add(dataInfos[i]);
//        }
    }

    /**
     * 控件的点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_icon_image:
                // TODO: 2017/2/20 开发上传头像功能
                Toast.makeText(MainActivity.this, "你点击了头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_name:
                // TODO: 2017/2/20 开发更改用户名功能
                Toast.makeText(MainActivity.this, "用户名：" + "user_name: Jerry", Toast.LENGTH_SHORT).show();
                break;
            case R.id.email:
                // TODO: 2017/2/20 开发更改邮箱地址功能
                Toast.makeText(MainActivity.this, "邮箱：" + "e-mail address： 2378624832@qq.com", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab:
                //FloatingActionButton的点击监听事件
                Snackbar.make(view, "切换模式", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isList) {
                                    isList = false;
                                    //切换网格模式
                                    //创建布局管理器  GridLayoutManager(垂直方向滚动，参数二表示列数，按列排序)
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                } else {
                                    isList = true;
                                    //切换列表模式
                                    //创建布局管理器
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                }
                                Toast.makeText(MainActivity.this, "Mode Changed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
        }
    }

    /**
     * 设置NavigationView中的menu的点击监听事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu_call:
                Toast.makeText(MainActivity.this, "call: 1234567890", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_friends:
                Toast.makeText(MainActivity.this, "你点击了朋友圈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_location:
                Toast.makeText(MainActivity.this, "当前位置： ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_mail:
                Toast.makeText(MainActivity.this, "mail: 1234567@qq.com", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_task:
                Toast.makeText(MainActivity.this, "我的任务：", Toast.LENGTH_SHORT).show();
                break;
        }
        //关闭DrawerLayout
        mDrawerLayout.closeDrawers();
        return true;
    }

    /**
     * Toolbar 创建menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Toolbar - menu的item的点击监听事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.toolbar_menu_backup:
                Toast.makeText(MainActivity.this, "你点击了Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_delete:
                Toast.makeText(MainActivity.this, "你点击了Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_settings:
                Toast.makeText(MainActivity.this, "你点击了Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    /**
     * 返回键退出应用(连按两次)
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long waitTime = 2000;
    private long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                finish();
                System.exit(0);
            }
            return true;
        } else if (KeyEvent.KEYCODE_HOME == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
