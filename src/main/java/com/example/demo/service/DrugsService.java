package com.example.demo.service;

import com.example.demo.entity.Drugs;
import com.example.demo.mapper.DrugsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DrugsService {
    @Autowired
    DrugsMapper drugsMapper;
    Set<String> titleSet = null;

    public void save(Drugs drug){
        drugsMapper.save(drug);
    }
    public Drugs getDrugs(Long id){
        return drugsMapper.getDrugs(id);
    }

    public String resolve(String filePath) {
        System.out.println("开始====================");
        int count = 0;
        String [] titleArr = new String[]{"通用名","【商品名】","剂型","【主要用途】","【常用剂量】","【如何使用该药】",
                "【用药后可能出现的不适症状】","【如何保存药品】","【窗口发药交代】","【起效时间和维持时间】",
                "【需监测的参数】","【特殊人群用药指导】"};
        titleSet = new HashSet<String>(Arrays.asList(titleArr));
        BufferedReader reader = null;
        StringBuilder line = null;
        String s = null,s1=null,s2=null;
        String title = null;
        Drugs drug = null;
        Map<String,String> drugMap = null;
        boolean reading = false;
        try {
            //按utf-8编码读取
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
            /* 按行读取
                1、空行，药品结束，保存药品，药品对象置为null
                2、非空行，药品对象为null，药品开始，创建对象，此时为第一行，取通用名
                3、“【”开头，新属性开始，旧属性结束
             */
            while ((s = reader.readLine()) != null) {
                if (s.trim().length()==0){//药品结束
                    if (drug != null) {
                        //保存最后一个属性值
                        if (line != null) {
                            this.putValue(drug,title,line.toString());
                        }
                        //保存对象
                        drugsMapper.save(drug);
                        count++;
                        drug = null;
                        line = null;
                        title = null;
                    }
                    continue;
                }
                if (drug == null) {//药品开始
                    drug = new Drugs();
                    line = new StringBuilder(s.trim());
                    title="通用名";
                    continue;
                }
                if (s.trim().startsWith("【")){//新属性开始
                    //保存上个属性值
                    if (line != null) {
                        this.putValue(drug,title,line.toString());
                    }
                    line = null;
                    title = null;
                    s = s.trim();
                    //判断新属性
                    if (s.indexOf("】")>0){
                        title = s.substring(0,s.indexOf("】")+1);
                        if(titleSet.contains(title)){//需要获取该属性值
                            line = new StringBuilder(s.substring(s.indexOf("】")+1));
                        }else{
                            title = null;
                        }
                    }
                }else{
                    if(titleSet.contains(title)){
                        //拼接属性值
                        if (line.length()>0){
                            line.append("\n");
                        }
                        line.append(s);
                    }
                }
            }
            if (drug != null) {
                if (line != null) {
                    this.putValue(drug,title,line.toString());
                }
                //保存对象
                drugsMapper.save(drug);
                count++;
            }
            reader.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束！共："+count+"个================");
        return "Finished！";
    }

    public void putValue(Drugs drug,String title,String value){
        if (value == null) {
            return;
        }
        value=value.trim();
        if ("通用名".equals(title)){
            String enName = "";
            String zhName = "";
            List<Integer> leftList = new ArrayList<Integer>();
            List<Integer> rightList = new ArrayList<Integer>();
            int fromIndex = 0;
            while (true){
                int ind = value.indexOf("（", fromIndex);
                if(ind!=-1){
                    leftList.add(ind);
                    fromIndex = ind+1;
                }else{
                    ind = value.indexOf("(", fromIndex);
                    if(ind!=-1){
                        leftList.add(ind);
                        fromIndex = ind+1;
                    }else{
                        break;
                    }
                }
            }
            fromIndex = 0;
            while (true){
                int ind = value.indexOf("）", fromIndex);
                if(ind!=-1){
                    rightList.add(ind);
                    fromIndex = ind+1;
                }else{
                    ind = value.indexOf(")", fromIndex);
                    if(ind!=-1){
                        rightList.add(ind);
                        fromIndex = ind+1;
                    }else{
                        break;
                    }
                }
            }
            Pattern p = Pattern.compile("[a-zA-Z]");
            if (leftList.size()>0 && rightList.size()>0) {
                Collections.sort(leftList);
                Collections.sort(rightList);
                for (int i =0;i<leftList.size();i++){
                    int lt = leftList.get(i);
                    int rt = rightList.get(i);
                    if (lt<rt){
                        String en = value.substring(lt+1, rt);
                        Matcher matcher = p.matcher(en);
                        if (matcher.find()){//匹配到英文
                            int sind = 0;
                            if (i>0){
                                sind = rightList.get(i-1)+1;
                            }
                            String zn = value.substring(sind,lt);
                            zn = zn.trim();
                            if (zn.startsWith(",")||zn.startsWith("，")){
                                zhName += zn;
                            }else{
                                zhName += "，"+zn;
                            }
                            enName +="，"+en;
                        }else{
                            leftList.remove(i);
                            rightList.remove(i);
                            i--;
                        }
                    }
                }
                if (zhName.startsWith(",")||zhName.startsWith("，")){
                    zhName = zhName.substring(1);
                }
                if (enName.startsWith(",")||enName.startsWith("，")){
                    enName = enName.substring(1);
                }
            }
            if (enName.length()==0){
                enName = value;
            }
            if (zhName.length()==0){
                zhName = value;
            }
            drug.setEnName(enName!=null?enName.trim():null);
            drug.setZhName(zhName!=null?zhName.trim():null);
        }else if ("【商品名】".equals(title)){
            drug.setGoodsName(value);
        }else if ("剂型".equals(title)){
            drug.setDosageForm(value);
        }else if ("【主要用途】".equals(title)){
            drug.setUse(value);
        }else if ("【常用剂量】".equals(title)){
            drug.setCommonDoseosage(value);
        }else if ("【如何使用该药】".equals(title)){
            drug.setUseage(value);
        }else if ("【用药后可能出现的不适症状】".equals(title)){
            String str1 = "常见:";
            int index1 = value.indexOf(str1);
            if (index1==-1){
                str1 = "常见：";
                index1 = value.indexOf(str1);
            }
            if (index1==-1){
                str1 = "常见";
                index1 = value.indexOf(str1);
            }
            String str2 = "严重：";
            int index2 = value.indexOf(str2);
            if (index2==-1){
                str2 = "严重:";
                index2 = value.indexOf(str2);
            }
            if (index2==-1){
                str2 = "严重";
                index2 = value.indexOf(str2);
            }
            String common = null,serious=null;
            if (index1>=0){
                if (index2>=0){
                    if (index1<index2){
                        common = value.substring(index1+str1.length(),index2);
                        serious = value.substring(index2+str2.length());
                    }else{
                        serious = value.substring(index2+str2.length(),index1);
                        common = value.substring(index1+str1.length());
                    }
                }else{
                    common = value.substring(index1+str1.length());
                }
            }else if (index2>=0){
                serious = value.substring(index2+str2.length());
            }
            drug.setAdrerseReactionsCommon(common!=null?common.trim():null);
            drug.setAdrerseReactionsSerious(serious!=null?serious.trim():null);
        }else if ("【如何保存药品】".equals(title)){
            drug.setStorage(value);
        }else if ("【窗口发药交代】".equals(title)){
            drug.setWindowTell(value);
        }else if ("【起效时间和维持时间】".equals(title)){
            drug.setEffectiveTime(value);
        }else if ("【需监测的参数】".equals(title)){
            drug.setMonitoringParameters(value);
        }else if ("【特殊人群用药指导】".equals(title)){
            drug.setGuidance(value);
        }
    }
}
