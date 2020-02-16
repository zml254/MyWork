# Snake
## app简介：
这个是我自己突发奇想写了一个贪吃蛇的游戏，通过玩游戏让人们快乐，此app点击直接启动，由于贪吃蛇游戏规则过于简单，所以app内没有介绍游戏规则。
## 技术：
  ![image](https://github.com/zml254/MyWork/blob/master/image/login.png)   ![image](https://github.com/zml254/MyWork/blob/master/image/register.png)<br>
  ![image](https://github.com/zml254/MyWork/blob/master/image/base.png) ![image](https://github.com/zml254/MyWork/blob/master/image/navigation.png)<br>
  app主界面使用drawerlayout，toolbar，在滑动菜单页面使用navigationview并其中menu的注册点击事件<br>
  ![image](https://github.com/zml254/MyWork/blob/master/image/joke.png)<br>
  在此界面中使用coordinatorlayout和cardview并且让通知栏随屏幕滚动而隐藏和显示<br>
  ![image](https://github.com/zml254/MyWork/blob/master/image/game.png)<br>
  app中我使用到一个自定义的View，用Paint规划贪吃蛇行走的背景，并和5个控制按钮写入一个RelativeLayout中。<br>
  当游戏结束时，使用一个自定义的Dialog来与用户互动，并了解其是否需要继续游戏，并记录下此时的得分，与文件中的记录进行比较并决定是否修改。<br>
  若app结束但游戏并未结束时，将使用SharedPreferences存储蛇的各项数据，并在下一次游戏运行时弹出Dialog询问是否需要继续游戏。<br>
  游戏开始时，启动前台服务弹出一个通知报告游戏的运行情况，而且监听了手机的home键和多任务键，在游戏中使用后会暂替游戏，储存数据并提醒用户，
此时改变前台通知，如果想要继续游戏只需点击通知执行恢复游戏。<br>
  使用Timer来对activity进行定时刷新，从而达到蛇运动的效果。使用Handler来实现主线程与子线程的信息传递<br>
