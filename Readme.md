#Xmot
This a simple Android app which helps you look up meanings of french words **offline** and **online**. You can also recite words and store them in your local **notebook**. The conncetion between server and client is based on **TCP**.  

![Welcome UI](/Users/shicanjie/Desktop/WelcomUI.png)
##Basic functions/基本功能
###Database/词库
Lingoes Dictionary：法汉词典  
You can download [here](http://www.lingoes.cn/zh/dictionary).

###Search word meanings/查单词
Search on the **local database** first. Then send request to the server, server will look up the word on [法语助手](http://www.frdic.com) and send the meaning back to the client.  

![Searchword](/Users/shicanjie/Desktop/Searchword.png)
###Recite words/背单词
####Rocket Recitation Mode/极速背词
Thanks to the [Swpieable-Cards](https://github.com/kikoso/Swipeable-Cards) provided by kikoso.  

![Rocket](/Users/shicanjie/Desktop/Rocket.png)

Every time ten words randomly selected from the local database will be displayed.  
####Normal Recitation Mode/顺序背词
For the wrong answer, a message of "Wrong Answer" will display. At the same time, it will send a message to the server and store the word in your notebook on server.  
<pre>class MyClickListener implements View.OnClickListener {
        private Integer pos;
        public MyClickListener(Integer Pos){
            this.pos = Pos;
        }
        public void onClick(View v) {
            Toast.makeText(NormalModeActivity.this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            SendWrongWord(this.pos);
        }
    }
</pre>
<pre>
class Connect_Thread extends Thread implements Runnable//继承Thread
    {
        private String Question;
        private String Answer;
        public Connect_Thread(int i){
            this.Question = Questions.get(i).GetQuestion();
            this.Answer = Questions.get(i).GetAnswer();
        }
        public void run()
        {
            try {
                Log.d("NormalModeActivity", "/store " +"#" + User.get_Username() + "#" + Question + "#" + Answer);
                socket_helper.getsocket();
                socket_helper.sendMessage("/store " + "#" + User.get_Username()+ "#" + Question + "#" + Answer);
            }
            catch (Exception ex){
                Log.d("NormalModeActivity", "run: " + ex.getMessage());
            }
        }
    }
</pre>
![NormalMode](/Users/shicanjie/Desktop/normal.png)
##Login/用户登陆
Make sure that you have set the right IP address in class **MyTCPSocket**.  

![IP](/Users/shicanjie/Desktop/IP.png)
![Wifi](/Users/shicanjie/Desktop/wifi.png)

The new user should register at first and then log in. 
 
![Login](/Users/shicanjie/Desktop/login.png) 
##Contact
[halflkaka](https://github.com/halflkaka) raphael_shi@foxmail.com  
[ldihao](https://github.com/ldihao) 727260225@qq.com  
**We are looking forward to your contribution!**


    
