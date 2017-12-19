package com.ericsson.li.other;

import java.awt.*;   
import java.lang.*;   
import javax.swing.*;   
import javax.swing.event.*;   
import java.awt.event.*;   
import java.text.DecimalFormat;   
public class Calculator   
    implements ActionListener { //导入动作监听接口   
  //设计面板中的单位   
  int mid;   
  int fchange;   
  long ianswer;   
  long ivard;   
  JFrame frame;   
  JTextField textAnswer;//显示数值窗口   
  JPanel panel, panel1, panel2, panel3,p4;   
  JMenuBar mainMenu;//菜单容器   
  JTextField textMemory;//显示当前是否内存有数   
  JLabel labelMemSpace; //labelMemSpace单纯做摆设，控制面板的形状   
  JButton buttonBk, buttonCe, buttonC;//退格，清除，清0   
  JButton button[];//数字键数组   
  JButton buttonMC, buttonMR, buttonMS, buttonMAdd;//内存键   
  JButton bsin,bcos,bpi,bmod,bxy,bx3,bln,btan,bx2,bn;   
  JButton ba,bb,bc,bd,be,bf;   
  JButton buttonDot, buttonAddAndSub, buttonAdd, buttonSub, buttonMul, buttonDiv, buttonmod;//运算符键   
  JButton buttonsqrt, buttonDao, buttonEqual;//平方，倒数，开平方   
  ButtonGroup group;//单选组   
  JMenu editMenu, viewMenu, helpMenu;//菜单编辑，查看，帮助   
  JMenuItem copyItem, pasteItem, tItem, sItem, numberGroup, topHelp, aboutCal;   
  DecimalFormat df; //设置数据输出精度   
  JRadioButton rb1,rb2,rb3,rb4;   
  boolean clickable; //控制当前能否按键   
  boolean frist;//是否第一次按数字键   
  double memoryd; //使用内存中存储的数字   
  int memoryi;   
  double vard, answerd; //用来保存double型数据的中间值(vard)和最后结果(answerd)   
  short key = -1, prekey = -1; //key用来保存当前进行何种运算,prekey用来保存前次进行何种运算   
  String copy; //做复制用   
  JTextArea help; //帮助   
  JScrollPane scrollHelp;   
  //构造函数   
  public Calculator() {   
    rb1=new JRadioButton("16进制");   
    rb1.addActionListener(this);   
    rb2=new JRadioButton("10进制");   
    rb2.addActionListener(this);   
    rb3=new JRadioButton("2进制");   
    rb3.addActionListener(this);   
    rb4=new JRadioButton("8进制");   
    rb4.addActionListener(this);   
    group=new ButtonGroup();   
    group.add(rb1);   
    group.add(rb2);   
    group.add(rb3);   
    group.add(rb4);   
    rb2.setSelected(true);   
    p4=new JPanel();   
    p4.setLayout(new GridLayout(1,3));   
    p4.add(rb1);   
    p4.add(rb2);   
    p4.add(rb4);   
    p4.add(rb3);   
    clickable = true;//当前可以有键按下了   
    answerd = 0;   
    frist=true;   
    frame = new JFrame("王超的计算器");   
    df = new DecimalFormat("0.##############"); //设置数据输出精度(对于double型值)   
    textAnswer = new JTextField(15);//开辟一个15个字符的文本框空间   
    textAnswer.setEditable(false);//不可编辑   
    textAnswer.setBackground(new Color(255, 255, 255));   
    panel = new JPanel();   
    frame.getContentPane().add(panel);   
    panel1 = new JPanel();   
    panel2 = new JPanel();   
    panel.setLayout(new BorderLayout());//边界式布局   
    //设计整个面板   
    mainMenu = new JMenuBar();//菜单   
    editMenu = new JMenu("编辑(E)");   
    viewMenu = new JMenu("查看(V)");   
    helpMenu = new JMenu("帮助(H)");   
    copyItem = new JMenuItem("   复制(C) Ctrl+C");   
    copyItem.addActionListener(this);//事件监听复制   
    pasteItem = new JMenuItem("   粘贴(V) Ctrl+V");   
    pasteItem.addActionListener(this);//事件监听粘贴   
    editMenu.add(copyItem);//添加到菜单   
    editMenu.add(pasteItem);   
    tItem = new JMenuItem("   标准型(T)");//===============   
    tItem.addActionListener(this);   
    sItem = new JMenuItem("   科学型(S)");   
    sItem.addActionListener(this);   
    numberGroup = new JMenuItem("   数字分组(I)");   
    numberGroup.addActionListener(this);   
    viewMenu.add(tItem);   
    viewMenu.add(sItem);   
    viewMenu.add(numberGroup);   
    topHelp = new JMenuItem("   帮助主题(H)");//===============   
    topHelp.addActionListener(this);   
    help = new JTextArea(5, 20);//帮助主题弹出文本对话框   
    scrollHelp = new JScrollPane(help);   
    help.setEditable(false);   
    help.append("执行简单计算\n");   
    help.append("1.  键入计算的第一个数字。\n");   
    help.append("2.  单击“+”执行加、“-”执行减、“*”执行乘或“/”执行除。\n");   
    help.append("3.  键入计算的下一个数字。\n");   
    help.append("4.  输入所有剩余的运算符和数字。\n");   
    help.append("5.  单击“=”。\n");   
    aboutCal = new JMenuItem("   关于计算器(A)");   
    aboutCal.addActionListener(this);   
    helpMenu.add(topHelp);   
    helpMenu.add(aboutCal);   
    mainMenu.add(editMenu);   
    mainMenu.add(viewMenu);   
    mainMenu.add(helpMenu);   
    panel.add(mainMenu, BorderLayout.NORTH);//布局北菜单窗口   
    panel.add(textAnswer, BorderLayout.CENTER);//中显示窗口   
    panel.add(panel1, BorderLayout.SOUTH);//南按键窗口   
    panel1.setLayout(new BorderLayout());   
    textMemory = new JTextField(3);   
    textMemory.setEditable(false);   
    textMemory.setBackground(new Color(217, 217, 217));   
    labelMemSpace = new JLabel("      ");   
    buttonBk = new JButton("Backspace");   
    buttonBk.setForeground(new Color(255, 0, 0));   
    buttonCe = new JButton("ce");   
    buttonCe.setForeground(new Color(255, 0, 0));   
    buttonC = new JButton("c");   
    buttonC.setForeground(new Color(255, 0, 0));   
    buttonBk.addActionListener(this);   
    buttonCe.addActionListener(this);   
    buttonC.addActionListener(this);   
    panel1.add(panel2, BorderLayout.NORTH);   
    panel2.setLayout(new FlowLayout(FlowLayout.LEFT));//左对齐   
    panel2.add(textMemory);   
    panel2.add(buttonBk);   
    panel2.add(buttonCe);   
    panel2.add(buttonC);    
    panel2.add(p4);   
    panel3 = new JPanel();   
    panel1.add(panel3, BorderLayout.CENTER);   
    button = new JButton[10];   
    for (int i = 0; i < button.length; i++) {   
      button[i] = new JButton(Integer.toString(i));   
      button[i].setForeground(new Color(0, 0, 255));   
        
    }   
    bsin=new JButton("sin");   
    bsin.setForeground(new Color(0, 0, 255));   
    bcos=new JButton("cos");   
    bcos.setForeground(new Color(0, 0, 255));   
    btan=new JButton("tan");   
    btan.setForeground(new Color(0, 0, 255));   
    bpi=new JButton("pi");   
    bpi.setForeground(new Color(0, 0, 255));   
    bxy=new JButton("x^y");/////////////////////////////////////////////////   
    bxy.setForeground(new Color(0, 0, 255));   
    bx3=new JButton("x^3");   
    bx3.setForeground(new Color(0, 0, 255));   
    bln=new JButton("ln");   
    bln.setForeground(new Color(0, 0, 255));   
    bx2=new JButton("x^2");   
    bx2.setForeground(new Color(0, 0, 255));   
    bmod=new JButton("mod");   
    bmod.setForeground(new Color(0, 0, 255));   
    bn=new JButton("n!");   
    bn.setForeground(new Color(0, 0, 255));   
    ba=new JButton("a");   
    ba.setForeground(new Color(0, 0, 255));   
    bb=new JButton("b");   
    bb.setForeground(new Color(0, 0, 255));   
    bc=new JButton("c");   
    bc.setForeground(new Color(0, 0, 255));   
    bd=new JButton("d");   
    bd.setForeground(new Color(0, 0, 255));   
    be=new JButton("e");   
    be.setForeground(new Color(0, 0, 255));   
    bf=new JButton("f");   
    bf.setForeground(new Color(0, 0, 255));   
    buttonMC = new JButton("MC");   
    buttonMC.setForeground(new Color(255, 0, 0));   
    buttonMR = new JButton("MR");   
    buttonMR.setForeground(new Color(255, 0, 0));   
    buttonMS = new JButton("MS");   
    buttonMS.setForeground(new Color(255, 0, 0));   
    buttonMAdd = new JButton("M+");   
    buttonMAdd.setForeground(new Color(255, 0, 0));   
    buttonDot = new JButton(".");   
    buttonDot.setForeground(new Color(0, 0, 255));   
    buttonAddAndSub = new JButton("+/-");   
    buttonAddAndSub.setForeground(new Color(0, 0, 255));   
    buttonAdd = new JButton("+");   
    buttonAdd.setForeground(new Color(255, 0, 0));   
    buttonSub = new JButton("-");   
    buttonSub.setForeground(new Color(255, 0, 0));   
    buttonMul = new JButton("*");   
    buttonMul.setForeground(new Color(255, 0, 0));   
    buttonDiv = new JButton("/");   
    buttonDiv.setForeground(new Color(255, 0, 0));   
    buttonmod = new JButton("%");   
    buttonmod.setForeground(new Color(0, 0, 255));   
    buttonsqrt = new JButton("sqrt");   
    buttonsqrt.setForeground(new Color(0, 0, 255));   
    buttonDao = new JButton("1/x");   
    buttonDao.setForeground(new Color(0, 0, 255));   
    buttonEqual = new JButton("=");   
    buttonEqual.setForeground(new Color(255, 0, 0));   
    buttonMC = new JButton("MC");   
    buttonMC.setForeground(new Color(255, 0, 0));   
    //将所有行为与监听绑定   
    panel3.setLayout(new GridLayout(5, 8));   
    panel3.add(buttonMC);   
    buttonMC.addActionListener(this);   
    panel3.add(button[7]);   
    button[7].addActionListener(this);   
    panel3.add(button[8]);   
    button[8].addActionListener(this);   
    panel3.add(button[9]);   
    button[9].addActionListener(this);   
    panel3.add(buttonDiv);   
    buttonDiv.addActionListener(this);   
    panel3.add(buttonsqrt);   
    buttonsqrt.addActionListener(this);   
     panel3.add(bsin);   
    bsin.addActionListener(this);   
     panel3.add(bcos);   
    bcos.addActionListener(this);   
    panel3.add(buttonMR);   
    buttonMR.addActionListener(this);   
    panel3.add(button[4]);   
    button[4].addActionListener(this);   
    panel3.add(button[5]);   
    button[5].addActionListener(this);   
    panel3.add(button[6]);   
    button[6].addActionListener(this);   
    panel3.add(buttonMul);   
    buttonMul.addActionListener(this);   
    panel3.add(buttonmod);   
    buttonmod.addActionListener(this);   
     panel3.add(btan);   
    btan.addActionListener(this);   
         panel3.add(bpi);   
    bpi.addActionListener(this);   
    panel3.add(buttonMS);   
    buttonMS.addActionListener(this);   
    panel3.add(button[1]);   
    button[1].addActionListener(this);   
    panel3.add(button[2]);   
    button[2].addActionListener(this);   
    panel3.add(button[3]);   
    button[3].addActionListener(this);   
    panel3.add(buttonSub);   
    buttonSub.addActionListener(this);   
    panel3.add(buttonDao);   
    buttonDao.addActionListener(this);   
    panel3.add(bmod);   
    bmod.addActionListener(this);   
     panel3.add(bxy);//////////////////////////////////////////////////////   
    bxy.addActionListener(this);   
    panel3.add(buttonMAdd);   
    buttonMAdd.addActionListener(this);   
    panel3.add(button[0]);   
    button[0].addActionListener(this);   
    panel3.add(buttonAddAndSub);   
    buttonAddAndSub.addActionListener(this);   
    panel3.add(buttonDot);   
    buttonDot.addActionListener(this);   
    panel3.add(buttonAdd);   
    buttonAdd.addActionListener(this);   
    panel3.add(buttonEqual);   
    buttonEqual.addActionListener(this);   
     panel3.add(bn);   
    bn.addActionListener(this);   
     panel3.add(bx2);   
    bx2.addActionListener(this);   
    panel3.add(ba);   
    ba.addActionListener(this);   
    panel3.add(bb);   
    bb.addActionListener(this);   
    panel3.add(bc);   
    bc.addActionListener(this);   
    panel3.add(bd);   
    bd.addActionListener(this);   
    panel3.add(be);   
    be.addActionListener(this);   
    panel3.add(bf);   
    bf.addActionListener(this);   
    panel3.add(bx3);   
    bx3.addActionListener(this);   
   panel3.add(bln);   
    bln.addActionListener(this);   
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);   
    visi();   
    textAnswer.setText("0");   
    fsix();   
    mid=2;   
    frame.pack();   
    frame.show();   
    fchange=2;   
       
       
  }   
  //设置各个按钮行为   
  public void actionPerformed(ActionEvent event) {   
    boolean sign = false; //判断是否是double型数参与运算，是为true，不是为false   
    Object temp = event.getSource();   
     try {   
      //如果按下数据按钮，将按下的按钮代表的数据插入的当前文本框字符串之后   
      for (int i = 0; i <= 9; i++)   
        if (temp == button[i] && clickable == true){////0-------9   
            String s = textAnswer.getText();   
                if (s.charAt(0)== '0' )   
                     frist=false;   
            if(frist==true)   
            textAnswer.setText(textAnswer.getText() + String.valueOf(i));//Integer.toString(i));   
            else    
         { textAnswer.setText("");   
        textAnswer.setText(textAnswer.getText() + String.valueOf(i));//Integer.toString(i));      
           frist=true;}   
        }   
          if (temp == ba && clickable == true){/////AAAAAAAAAAA   
            String s = textAnswer.getText();   
                if (s.charAt(0)== '0' )   
                     frist=false;   
            if(frist==true)   
            textAnswer.setText(textAnswer.getText() + "a");   
            else    
         { textAnswer.setText("");   
        textAnswer.setText(textAnswer.getText() + "a");      
           frist=true;}   
        }   
        if (temp == bb && clickable == true){/////BBBBBBBBBBBBBBB   
           String s = textAnswer.getText();   
                if (s.charAt(0)== '0' )   
                     frist=false;   
            if(frist==true)   
            textAnswer.setText(textAnswer.getText() + "b");   
            else    
         { textAnswer.setText("");   
        textAnswer.setText(textAnswer.getText() + "b");      
           frist=true;}   
        }   
        if (temp == bc && clickable == true){/////CCCCCCCCCCCCCCCCC   
        String s = textAnswer.getText();   
                if (s.charAt(0)== '0' )   
                     frist=false;   
            if(frist==true)   
            textAnswer.setText(textAnswer.getText() + "c");   
            else    
         { textAnswer.setText("");   
        textAnswer.setText(textAnswer.getText() + "c");      
           frist=true;}   
        }   
        if (temp == bd && clickable == true){/////DDDDDDDDDDDDDDDDDD   
        String s = textAnswer.getText();   
                if (s.charAt(0)== '0' )   
                     frist=false;   
            if(frist==true)   
            textAnswer.setText(textAnswer.getText() + "d");   
            else    
         { textAnswer.setText("");   
        textAnswer.setText(textAnswer.getText() + "d");      
           frist=true;}   
        }   
        if (temp == be && clickable == true){/////EEEEEEEEEEEEEEEEEEE   
        String s = textAnswer.getText();   
                if (s.charAt(0)== '0' )   
                     frist=false;   
            if(frist==true)   
            textAnswer.setText(textAnswer.getText() + "e");   
            else    
         { textAnswer.setText("");   
        textAnswer.setText(textAnswer.getText() + "e");      
           frist=true;}   
        }   
        if (temp == bf && clickable == true){/////FFFFFFFFFFFFFFFFFF   
       String s = textAnswer.getText();   
                if (s.charAt(0)== '0' )   
                     frist=false;   
            if(frist==true)   
            textAnswer.setText(textAnswer.getText() + "f");   
            else    
         { textAnswer.setText("");   
        textAnswer.setText(textAnswer.getText() + "f");      
           frist=true;}   
        }   
          //按下'.'按钮时，判断当前文本框内字符串中含不含'.'，如果已含，则不允许再插入'.'   
      if (temp == buttonDot && clickable == true) {   
        boolean isDot = false;   
        if (textAnswer.getText().length() == 0)   
          isDot = true;   
        for (int i = 0; i < textAnswer.getText().length(); i++)//小数点判断，有就为TRUE   
          if ('.' == textAnswer.getText().charAt(i)) {   
            isDot = true;   
            break;   
          }   
        if (isDot == false)   
          textAnswer.setText(textAnswer.getText() + ".");   
      }   
      if ( (temp == buttonAdd || temp == buttonSub || temp == buttonMul ||   
            temp == buttonDiv)&& clickable == true) {   
        //'+'操作   
        if (temp == buttonAdd) {   
         sele();   
          prekey = key = 0;   
        }   
        //'-'操作   
        if (temp == buttonSub) {   
          sele();   
          prekey = key = 1;   
        }   
        //'*'操作   
        if (temp == buttonMul) {   
         sele();   
          prekey = key = 2;   
        }   
        //'/'操作   
        if (temp == buttonDiv) {   
           sele();   
            prekey = key = 3;   
        }   
          
      }   
          
      //'='操作   
      if (temp == buttonEqual && clickable == true) {   
        //如果连续按'=',则进行连续运算   
       if (fchange==2)   
       {equal();   
       }else{   
        fristchange();   
       equal1();   
       lastchange();   
       }   
          
      }   
      //'%'操作，对第二个操作数除以100   
      if (temp == buttonmod && clickable == true) {   
        frist=false;   
        if (answerd == 0) {   
          String s = textAnswer.getText();   
          textAnswer.setText(s);   
        }   
        else {   
          boolean isDot = false;   
          for (int i = 0; i < textAnswer.getText().length(); i++)   
            if ('.' == textAnswer.getText().charAt(i)) {   
              isDot = true;   
              break;   
            }   
          //如果是double数，除100   
          if (isDot == true) {   
            double dtemp = Double.parseDouble(textAnswer.getText());   
            dtemp = dtemp / 100.0;   
            textAnswer.setText(Double.toString(dtemp));   
          }   
          else {   
            //如果是int数但能被100整除，则去掉末尾两个零   
            if (Integer.parseInt(textAnswer.getText()) % 100 == 0) {   
              int itemp = Integer.parseInt(textAnswer.getText());   
              itemp /= 100;   
              textAnswer.setText(Integer.toString(itemp));   
            }   
            //如果是int数，但不能被100整除，则按double数处理   
            else {   
              double dtemp = Double.parseDouble(textAnswer.getText());   
              dtemp = dtemp / 100.0;   
              textAnswer.setText(Double.toString(dtemp));   
            }   
          }   
        }   
      }   
      //开根号运算   
      if (temp ==buttonsqrt && clickable == true) {   
        frist=false;   
        String s = textAnswer.getText();   
        if (s.charAt(0) == '-') {   
          textAnswer.setText("负数不能开根号");   
          clickable = false;   
        }   
        else   
          textAnswer.setText(Double.toString(java.lang.Math.sqrt(Double.   
              parseDouble(textAnswer.getText()))));   
      }   
         
      //倒数运算   
      if (temp == buttonDao && clickable == true) {   
        frist=false;   
        if (textAnswer.getText().charAt(0) == '0' &&   
            textAnswer.getText().length() == 1) {   
          textAnswer.setText("零不能求倒数");   
          clickable = false;   
        }   
        else {   
          boolean isDec = true;   
          int i, j, k;   
          String s = Double.toString(1 / Double.parseDouble(textAnswer.getText()));   
          for (i = 0; i < s.length(); i++)   
            if (s.charAt(i) == '.')   
              break;   
          for (j = i + 1; j < s.length(); j++)   
            if (s.charAt(j) != '0') {   
              isDec = false;   
              break;   
            }   
          if (isDec == true) {   
            String stemp = "";   
            for (k = 0; k < i; k++)   
              stemp += s.charAt(k);   
            textAnswer.setText(stemp);   
          }   
          else   
            textAnswer.setText(s);   
        }   
      }   
      //+++++++++++++++科学计算方法//sin值++++++++++++++++++++++++++++++++++++++   
      if (temp == bsin && clickable == true) {//sin   
       frist=false;   
       int tInt =Integer.parseInt(textAnswer.getText());   
    double tDou = tInt * 3.1415926/180;   
      textAnswer.setText(""+Math.sin(tDou));   
          }   
      if (temp == bcos && clickable == true) {//cos   
       frist=false;   
                 int tInt =Integer.parseInt(textAnswer.getText());   
    double tDou = tInt * 3.1415926/180;   
     textAnswer.setText(""+Math.cos(tDou));   
      }   
      if (temp == btan && clickable == true) {//tan   
       frist=false;   
                 int tInt =Integer.parseInt(textAnswer.getText());   
    double tDou = tInt * 3.1415926/180;   
      textAnswer.setText(""+Math.tan(tDou));   
      }   
      if (temp == bpi && clickable == true) {//pi   
       frist=false;   
          textAnswer.setText(Double.toString(java.lang.Math.PI*(Double.   
              parseDouble(textAnswer.getText()))));   
      }   
       if (temp == bln && clickable == true) {//ln   
          frist=false;   
          if(fchange==2)   
          {textAnswer.setText(Double.toString(java.lang.Math.log(Double.   
              parseDouble(textAnswer.getText()))));}   
          else   
          { fristchange();   
            textAnswer.setText(Double.toString(java.lang.Math.log(Integer   
                .parseInt(textAnswer.getText()))));   
            long jj=Math.round((Double.parseDouble(textAnswer.getText())));    
            textAnswer.setText(String.valueOf(jj));   
               lastchange();    }       
              }   
      if (temp == bmod && clickable == true) {//mod   
          frist=false;   
          if(fchange==2){answerd = Double.parseDouble(textAnswer.getText());   
          }else{   
            fristchange();   
            ianswer=Integer.parseInt(textAnswer.getText());   
            lastchange();   
          }   
             
         //textAnswer.setText(String.valueOf(answerd));    
          prekey = key = 4;   
      }   
      if (temp == bn && clickable == true) {//n   
       int i,s=1;   
       frist=false;   
        fristchange();   
       long n=Math.round((Integer.parseInt(textAnswer.getText())));     
            
         if(n>0){   
            for (i=1;i<=n;i++)   
            {s*=i;}   
         }else{   
            for (i=-1;i>=n;i--)   
            {   s*=i;   }   
            }   
         textAnswer.setText(String.valueOf(s));   
         lastchange();     
      }   
       if (temp == bx2 && clickable == true) {//bx2   
       frist=false;   
       if(fchange==2){   
        double o;   
       o =  Double.parseDouble(textAnswer.getText());   
        textAnswer.setText(String.valueOf(o*=o));   
       }else {   
       fristchange();   
       long o;   
       o =  Integer.parseInt(textAnswer.getText());   
        textAnswer.setText(String.valueOf(o*=o));   
        lastchange();}   
          
      }   
      if (temp == bx3 && clickable == true) {//bx3   
       frist=false;   
       if(fchange==2){   
        double o,o1;   
        o = Integer.parseInt(textAnswer.getText());   
        o1=o;   
        o*=o;   
        textAnswer.setText(String.valueOf(o*=o1));   
       }else {   
       fristchange();   
        long o,o1;   
        o = Integer.parseInt(textAnswer.getText());   
        o1=o;   
        o*=o;   
        textAnswer.setText(String.valueOf(o*=o1));   
        lastchange();   
      }   
      }   
      if (temp == bxy && clickable == true) {//bxy   
           frist=false;   
              
       if(fchange==2)   
       {answerd = Double.parseDouble((textAnswer.getText()));   
       }else{   
        fristchange();   
        ianswer = Integer.parseInt(textAnswer.getText());   
        lastchange();   
       }   
          
        prekey = key =6;   
      }   
      //按下'+/-'按钮时处理   
      if (temp == buttonAddAndSub && clickable == true) {   
        boolean isNumber = true;   
        String s = textAnswer.getText();   
        //S记录当前显示框中的数字   
        for (int i = 0; i < s.length(); i++)   
          if (! (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.' ||   
                 s.charAt(i) == '-')) {   
            isNumber = false;   
            break;   
          }   
        if (isNumber == true) {   
          //如果当前字符串首字母有'-'号,代表现在是个负数,再按下时,则将首符号去掉   
          if (s.charAt(0) == '-') {   
            textAnswer.setText("");//清空显示框，把S记录中原数据显示回来   
            for (int i = 1; i < s.length(); i++) {   
              char a = s.charAt(i);   
              textAnswer.setText(textAnswer.getText() + a);   
            }   
          }   
          //如果当前字符串第一个字符不是符号，则添加一个符号在首字母处   
          else   
            textAnswer.setText('-' + s);   
        }   
      }   
      //进制转换***********************************   
      if(temp==rb1){//16进制   
        tempmid();   
        tsix();//使用ABCDEF键   
        fbutton();//关闭SIN，COS等标准计算器功能   
        tnumball();   
        textAnswer.setText( Integer.toHexString(Integer   
        .parseInt(textAnswer.getText())));   
        fchange=1;   
        mid=1;   
        vard = answerd = 0;   
        ivard=ianswer=0;   
        }   
      else if(temp==rb2){//10进制   
        fsix();   
        tbutton();   
        tnumball();   
        tempmid();   
        long jj=Math.round((Double.parseDouble(textAnswer.getText())));   
        textAnswer.setText( String.valueOf(jj));   
        mid=2;   
        fchange=2;   
        vard = answerd = 0;   
        ivard=ianswer=0;           
        }   
      else if(temp==rb4) {//8进制   
        fnumb8();tempmid();   
        fsix();//禁止ABCDEF键   
        fbutton();//关闭SIN，COS等标准计算器功能   
        textAnswer.setText(Integer.toOctalString(Integer   
        .parseInt(textAnswer.getText())));   
        fchange=4;   
        mid=4;   
        vard = answerd = 0;   
        ivard=ianswer=0;           
        }   
      else if(temp==rb3){//2进制   
        fsix();fbutton();fnumb2();   
        vard = answerd = 0;   
        ivard=ianswer=0;   
        tempmid();   
        textAnswer.setText(Integer.toBinaryString(Integer   
        .parseInt(textAnswer.getText())));   
        fchange=3;   
        mid=3;   
        }   
      //计算器有关内存操作==========================================   
      //'MC'的操作，将内存清0   
      if (temp == buttonMC && clickable == true) {   
        frist=false;   
        memoryd = memoryi = 0;   
        textMemory.setText("");   
      }   
      //'MS'的操作，将当前文本框内容保存入内存，显示'M'   
      if (temp == buttonMS && clickable == true) {   
        frist=false;   
        boolean isDot = false;   
        textMemory.setText("   M");   
        for (int i = 0; i < textAnswer.getText().length(); i++)   
          if ('.' == textAnswer.getText().charAt(i)) {   
            isDot = true;   
            break;   
          }   
        //如果是double,则存入memoryd(double存储器)   
        if (isDot == true) {   
               
          memoryd = Double.parseDouble(textAnswer.getText());   
          memoryi = 0; //保证存储器中存放最新的值   
        }   
        //如果是int,则存入memoryi(int存储器)   
        else {   
          memoryi = Integer.parseInt(textAnswer.getText());   
          memoryd = 0; //保证存储器中存放最新的值   
        }   
      }   
      //'MR'的操作，将存储器中的信息输出   
      if (temp == buttonMR && clickable == true) {   
        frist=false;   
        if (memoryd != 0)   
          textAnswer.setText(Double.toString(memoryd));   
        if (memoryi != 0)   
          textAnswer.setText(Integer.toString(memoryi));   
      }   
      //'M+'的功能，将当前文本框里的数据和存储器中数据相加后，再存入存储器   
      if (temp == buttonMAdd && clickable == true) {   
        frist=false;   
        boolean isDot = false;   
        for (int i = 0; i < textAnswer.getText().length(); i++)   
          if ('.' == textAnswer.getText().charAt(i)) {   
            isDot = true;   
            break;   
          }   
        if (memoryi != 0) { //存储中是一个int型数   
          if (isDot == false) //被加数是一个int型数   
            memoryi += Integer.parseInt(textAnswer.getText());   
          else { //被加数是一个double型数，则将int存储器中数传入double存储器与当前数相加，int存储器清零   
            memoryd = memoryi + Double.parseDouble(textAnswer.getText());   
            memoryi = 0;   
          }   
        }   
        else   
          memoryd += Double.parseDouble(textAnswer.getText());   
      }   
      //按下'Backspace'键，利用循环将当前字符串中的最后一个字母删除   
      if (temp == buttonBk && clickable == true) {   
        String s = textAnswer.getText();   
        textAnswer.setText("");   
        for (int i = 0; i < s.length() - 1; i++) {   
          char a = s.charAt(i);   
          textAnswer.setText(textAnswer.getText() + a);   
        }   
      }   
      //按下'CE'按钮，将当前文本框内数据清除   
      if (temp == buttonCe) {   
        textAnswer.setText("0");   
        clickable = true;   
      }   
      //按下'C'按钮，文本框内数据清除，同时var,answer清0   
      if (temp == buttonC) {   
        vard = answerd = 0;   
        ivard=ianswer=0;   
        textAnswer.setText("0");   
        clickable = true;   
      }   
      //按下'复制'菜单栏   
      if (temp == copyItem) {   
        copy = textAnswer.getText();   
      }   
      //按下'粘贴'菜单栏   
      if (temp == pasteItem) {   
        textAnswer.setText(copy);   
      }   
      if (temp == sItem) {   
        frame.setTitle("科学计算器");   
        view();   
        frame.pack();   
      }   
      if (temp == tItem) {   
       frame.setTitle("标准计算器");   
       rb2.setSelected(true);   
       tnumball();   
       tbutton();   
       visi();   
       frame.pack();   
      }   
      //按下'帮助主题'菜单栏   
      if (temp == topHelp) {   
        JOptionPane.showMessageDialog(panel, scrollHelp);   
      }   
      //按下'数字分组'菜单栏   
      if (temp == numberGroup) {   
        if (numberGroup.getText().compareTo("   数字分组(I)") == 0)   
          numberGroup.setText("√数字分组(I)");   
        else   
          numberGroup.setText("   数字分组(I)");   
      }   
      //按下'关于'菜单栏   
      if (temp == aboutCal) {   
        JOptionPane.showMessageDialog(panel, "计算器1.00版\n开发者：王超");   
      }   
    }   
    //输入中如果有操作非法，比如按下两次'+'，捕获异常   
    catch (Exception e) {   
      textAnswer.setText("操作非法");   
      clickable = false;   
    }   
      
  }   
  //主函数   
  public static void main(String args[]) {   
    new Calculator();   
  }   
  public void visi()//隐藏科学计算器的功能，变成标准计算器   
  { buttonsqrt.setEnabled(true);   
    buttonmod.setEnabled(true);   
    ba.setVisible(false);   
    bb.setVisible(false);   
    bc.setVisible(false);   
    bd.setVisible(false);   
    be.setVisible(false);   
    bf.setVisible(false);   
    bx3.setVisible(false);   
    bln.setVisible(false);   
    bsin.setVisible(false);   
    bcos.setVisible(false);   
    bxy.setVisible(false);   
    bx2.setVisible(false);   
    bn.setVisible(false);   
    btan.setVisible(false);   
    bpi.setVisible(false);   
    bmod.setVisible(false);   
    p4.setVisible(false);   
  }   
  public void view()//显示科学计算器的功能键，变成科学计算器   
  {buttonsqrt.setEnabled(false);   
    buttonmod.setEnabled(false);   
    ba.setVisible(true);   
    bb.setVisible(true);   
    bc.setVisible(true);   
    bd.setVisible(true);   
    be.setVisible(true);   
    bf.setVisible(true);   
    bx3.setVisible(true);   
    bln.setVisible(true);   
    bsin.setVisible(true);   
    bcos.setVisible(true);   
    bxy.setVisible(true);   
    bx2.setVisible(true);   
    bn.setVisible(true);   
    btan.setVisible(true);   
    bpi.setVisible(true);   
    bmod.setVisible(true);   
    p4.setVisible(true);   
  }   
  public void fsix()//十六进制按键不可以用   
  {ba.setEnabled(false);   
    bb.setEnabled(false);   
    bc.setEnabled(false);   
    bd.setEnabled(false);   
    be.setEnabled(false);   
    bf.setEnabled(false);   
       
  }   
  public void tsix()//十六进制按键可以用   
  {     ba.setEnabled(true);   
    bb.setEnabled(true);   
    bc.setEnabled(true);   
    bd.setEnabled(true);   
    be.setEnabled(true);   
    bf.setEnabled(true);   
       
  }   
  public void fbutton()//函数不可用   
  {   
    bsin.setEnabled(false);   
    bcos.setEnabled(false);   
    btan.setEnabled(false);   
    bpi.setEnabled(false);     
    buttonDao.setEnabled(false);   
    buttonDot.setEnabled(false);       
  }   
   public void tbutton()//函数可用   
   {   
    bsin.setEnabled(true);   
    bcos.setEnabled(true);   
    btan.setEnabled(true);   
    bpi.setEnabled(true);   
    buttonDao.setEnabled(true);     
    buttonDot.setEnabled(true);    
  }   
  public void fnumb2()//二进制按键，保有01，其它2-9不可用   
  {for (int i = 2; i <= 9; i++)   
        button[i].setEnabled(false);   
       
  }   
  public void tnumball()//所有键可用   
  {   
     for (int i = 0; i <= 9; i++)   
        button[i].setEnabled(true);   
       
  }   
  public void fnumb8()//八进制按键，可用1-8   
  { for (int i = 0; i <= 7; i++)   
         button[i].setEnabled(true);   
         button[9].setEnabled(false);   
        button[8].setEnabled(false);   
       
  }   
   public void tempmid()//判断上一次是什么进制   
   {   
    if (mid==1)   
    {   
    int decimal = Integer.parseInt(textAnswer.getText(),16);    
       textAnswer.setText(String.valueOf(decimal));   
  }   
  else if(mid==2){   
    int decimal = Integer.parseInt(textAnswer.getText(),10);    
       textAnswer.setText(String.valueOf(decimal));   
  }   
  else if(mid==3){   
    int decimal = Integer.parseInt(textAnswer.getText(),2);    
       textAnswer.setText(String.valueOf(decimal));   
  }   
 else if(mid==4){   
    int decimal = Integer.parseInt(textAnswer.getText(),8);    
       textAnswer.setText(String.valueOf(decimal));   
  }   
     
  }   
   public void fristchange()//other->10进制   
   {    if (fchange==1)   
    {// textAnswer.setText("ddd");   
    int decimal = Integer.parseInt(textAnswer.getText(),16);    
       textAnswer.setText(String.valueOf(decimal));   
  }   
  else if(fchange==2){   
    long jj=Math.round((Double.parseDouble(textAnswer.getText())));    
           textAnswer.setText(String.valueOf(jj));   
    //int decimal = Integer.parseInt(textAnswer.getText(),10);    
     //  textAnswer.setText(String.valueOf(decimal));   
  }   
  else if(fchange==3){   
    long jj=Math.round((Double.parseDouble(textAnswer.getText())));    
           textAnswer.setText(String.valueOf(jj));   
    int decimal = Integer.parseInt(textAnswer.getText(),2);    
       textAnswer.setText(String.valueOf(decimal));   
  }   
 else if(fchange==4){   
    long jj=Math.round((Double.parseDouble(textAnswer.getText())));    
           textAnswer.setText(String.valueOf(jj));   
    int decimal = Integer.parseInt(textAnswer.getText(),8);    
       textAnswer.setText(String.valueOf(decimal));   
  }   
     
  }   
   public void lastchange()//10->other进制   
   {    if (fchange==1)   
    {//textAnswer.setText(textAnswer.getText()+"fff");   
    textAnswer.setText( Integer.toHexString(Integer   
        .parseInt(textAnswer.getText())));   
  }   
  else if(fchange==2){   
    long jj=Math.round((Double.parseDouble(textAnswer.getText())));    
           textAnswer.setText(String.valueOf(jj));   
       
  }   
  else if(fchange==3){   
    long jj=Math.round((Double.parseDouble(textAnswer.getText())));    
           textAnswer.setText(String.valueOf(jj));   
    textAnswer.setText(Integer.toBinaryString(Integer   
        .parseInt(textAnswer.getText())));   
  }   
 else if(fchange==4){   
    long jj=Math.round((Double.parseDouble(textAnswer.getText())));    
           textAnswer.setText(String.valueOf(jj));   
    textAnswer.setText(Integer.toOctalString(Integer   
        .parseInt(textAnswer.getText())));   
  }   
   }   
   public void operater2()//标准计算器运算   
   {    frist=false;   
          switch (prekey) {   
            case 0:   
              ianswer += Integer.parseInt(textAnswer.getText());   
              break;   
            case 1:   
              ianswer -= Integer.parseInt(textAnswer.getText());   
              break;   
            case 2:   
              ianswer *=Integer.parseInt(textAnswer.getText());   
              break;   
            case 3:   
              if (Integer.parseInt(textAnswer.getText()) == 0) {   
                textAnswer.setText("除数不能为零");   
                clickable = false;   
              }   
              else   
              ianswer /= Integer.parseInt(textAnswer.getText());   
              break;   
            default:   
            //JOptionPane.showMessageDialog(null,""+ianswer)  ;   
              ianswer = Integer.parseInt(textAnswer.getText());   
          }   
       textAnswer.setText(String.valueOf(ianswer));   
    }   
    public void operater1()//十六进制，八进制，二进制，运算   
    {   frist=false;   
          switch (prekey) {   
            case 0:   
              answerd += Double.parseDouble(textAnswer.getText());   
              break;   
            case 1:   
              answerd -= Double.parseDouble(textAnswer.getText());   
              break;   
            case 2:   
              answerd *= Double.parseDouble(textAnswer.getText());   
              break;   
            case 3:   
              if (Double.parseDouble(textAnswer.getText()) == 0) {   
                textAnswer.setText("除数不能为零");   
                clickable = false;   
              }   
              else   
                answerd /= Double.parseDouble(textAnswer.getText());   
              break;   
            default:   
              answerd = Double.parseDouble(textAnswer.getText());   
          }   
       textAnswer.setText(String.valueOf(answerd));   
    }      
   public void equal()//浮点等于号得结果   
   {if (prekey == 5) {   
          if (key == 0) {   
            answerd += vard;   
            textAnswer.setText(df.format(answerd));   
          }   
          if (key == 1) {   
            answerd -= vard;   
            textAnswer.setText(df.format(answerd));   
          }   
          if (key == 2) {   
            answerd *= vard;   
            textAnswer.setText(df.format(answerd));   
          }   
          if (key == 3) {   
            if (Double.parseDouble(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              answerd /= vard;   
              textAnswer.setText(df.format(answerd));   
            }   
          }   
          if(key==4){   
             if (Double.parseDouble(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              answerd %= vard;   
              textAnswer.setText(df.format(answerd));   
            }   
          }   
           if(key==6){   
            int i;double x;    
            x=answerd;   
            int y=Integer.parseInt(textAnswer.getText());   
            for(i=1;i<y;i++)   
            {answerd*=x;   
            }   
             textAnswer.setText(String.valueOf(answerd));   
            }            
        }   
        else {   
          vard = Double.parseDouble(textAnswer.getText());   
          if (key == 0) {   
            prekey = -1;   
            answerd += Double.parseDouble(textAnswer.getText());   
            textAnswer.setText(df.format(answerd));   
          }   
          if (key == 1) {   
            prekey = -1;   
            answerd -= Double.parseDouble(textAnswer.getText());   
            textAnswer.setText(df.format(answerd));   
          }   
          if (key == 2) {   
            prekey = -1;   
            answerd *= Double.parseDouble(textAnswer.getText());   
            textAnswer.setText(df.format(answerd));   
          }   
          if (key == 3) {   
            prekey = -1;   
            if (Double.parseDouble(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              answerd /= Double.parseDouble(textAnswer.getText());   
              textAnswer.setText(df.format(answerd));   
            }   
          }   
            if(key==4){   
                 
             if (Double.parseDouble(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              answerd %= vard;   
              textAnswer.setText(df.format(answerd));   
            }   
          }   
          if(key==6){   
            int i;double x;    
            x=answerd;   
            int y=Integer.parseInt(textAnswer.getText());   
            for(i=1;i<y;i++)   
            {answerd*=x;   
            }   
            textAnswer.setText(String.valueOf(answerd));   
            }         
        }   
        prekey = 5;   
            frist=false;   
   }   
   public void equal1()//除十进制，求其它运算结果   
   {   
    if (prekey == 5) {   
          if (key == 0) {   
            ianswer += ivard;   
          }   
          if (key == 1) {   
            ianswer -= ivard;   
          }   
          if (key == 2) {   
            ianswer *= ivard;   
          }   
          if (key == 3) {   
            if (Integer.parseInt(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              ianswer /= ivard;   
            }   
          }   
          if(key==4){   
             if (Integer.parseInt(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              ianswer %= ivard;   
            }   
          }   
           if(key==6){   
            int i;double x;    
            x=ianswer;   
            int y=Integer.parseInt(textAnswer.getText());   
            for(i=1;i<y;i++)   
            {ianswer*=x;   
            }   
             textAnswer.setText(String.valueOf(ianswer));   
            }            
        }   
        else {   
          ivard = Integer.parseInt(textAnswer.getText());   
          if (key == 0) {   
            prekey = -1;   
            ianswer += Integer.parseInt(textAnswer.getText());   
                  }   
          if (key == 1) {   
            prekey = -1;   
            ianswer -= Integer.parseInt(textAnswer.getText());   
                      }   
          if (key == 2) {   
            prekey = -1;   
            ianswer *= Integer.parseInt(textAnswer.getText());   
                   }   
          if (key == 3) {   
            prekey = -1;   
            if (Integer.parseInt(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              ianswer /= Integer.parseInt(textAnswer.getText());   
                      }   
          }   
            if(key==4){   
             if (Integer.parseInt(textAnswer.getText()) == 0) {   
              textAnswer.setText("除数不能为零");   
              clickable = false;   
            }   
            else {   
              ianswer %= ivard;   
                 }   
          }   
          if(key==6){   
            int i;double x;    
            x=ianswer;   
            int y=Integer.parseInt(textAnswer.getText());   
            for(i=1;i<y;i++)   
            {ianswer*=x;   
            }   
               
            }textAnswer.setText(String.valueOf(ianswer));         
        }    
            prekey = 5;   
            frist=false;   
   }   
   public void sele()//四则运算，进制判断方法   
   {    if(fchange==2){   
          operater1();   
          }else{   
          fristchange();   
          operater2();   
          lastchange();   
            }   
   }   
   }  
