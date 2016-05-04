package main;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import game.Game;

public class Main {

	static final List<String> pricekeys = Arrays.asList("avgPlante","minPlante","buyPlante","sellPlante","avgPotion","minPotion","buyPotion","sellPotion");
	static int nSimulation = 10;
	
	static int nIteration = 8*7*24;
	static double alpha = 0.5;
	static int nPlante = 300;
	static double earnGold = 50;
	static int nPlayer = 100;
	static double pHerbe = 0.3439;
	static double pAlchemy = 0.3439;
	
	static String filename = "results/learning2/05-300-50-100";
	public static void main(String[] args) throws InterruptedException , FileNotFoundException, UnsupportedEncodingException {
		HashMap<String,ArrayList<Integer>> datas = new HashMap<String,ArrayList<Integer>>();
		
		for(String key : Game.keys){
			datas.put(key, new ArrayList<Integer>(nIteration));
			for(int i = 0; i < nIteration; i++){
				datas.get(key).add(0);
			}
		}
		ArrayList<String> dates = new ArrayList<String>();
		for(int i = 0; i < nSimulation; i++){
			Game game = new Game(alpha,nPlante,earnGold,nPlayer,pHerbe,pAlchemy);
			game.run(nIteration);
//			game.serveurUpdate();
//			game.run(nIteration/2);
			for(String key : Game.keys){
				for(int j = 0; j < nIteration; j++){
					datas.get(key).set(j, datas.get(key).get(j)+game.datas.get(key).get(j));
				}
			}
			dates = game.dates;
		}
		printResult(datas, dates);	
	}
	protected static PrintStream outputFile(String name) throws FileNotFoundException {
		return new PrintStream(new BufferedOutputStream(new FileOutputStream(name)));
	}
	
	protected static void printResult(HashMap<String,ArrayList<Integer>> datas, ArrayList<String> dates) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filename+".txt", "UTF-8");
		for(int i = 0; i<nIteration; i++){
			for(String key : Game.keys){
				if(pricekeys.contains(key))
					writer.print(datas.get(key).get(i)/(10000.0*nSimulation)+"\t");
				else
					writer.print(datas.get(key).get(i)/(nSimulation)+"\t");
			}
			writer.println(dates.get(i));
		}
		writer.close();
		writer = new PrintWriter(filename+"-Week"+".txt", "UTF-8");
		HashMap<String,ArrayList<Double>> weekDatas = new HashMap<String,ArrayList<Double>>();
		for(String key : Game.keys){
			weekDatas.put(key, new ArrayList<Double>(7*24));
			for(int i = 0; i < 7*24; i++){
				weekDatas.get(key).add(0.0);
			}
		}
		for(int i = 0; i<7*24; i++){
			for(int j = i; j < nIteration; j+=7*24){
				for(String key : Game.keys){
					if(pricekeys.contains(key))
						weekDatas.get(key).set(i, weekDatas.get(key).get(i)+datas.get(key).get(j)/(10000.0*nSimulation));
					else
						weekDatas.get(key).set(i, weekDatas.get(key).get(i)+datas.get(key).get(j)/(nSimulation));
				}
			}
		}
		int nWeek = nIteration/(7*24);
		for(int i = 0; i<7*24;i++){
			for(String key : Game.keys){
				writer.print(weekDatas.get(key).get(i)/nWeek+"\t");
			}
			writer.println(dates.get(i));
		}
		writer.close();
		writer = new PrintWriter(filename+"-Day"+".txt", "UTF-8");
		HashMap<String,ArrayList<Double>> dayDatas = new HashMap<String,ArrayList<Double>>();
		for(String key : Game.keys){
			dayDatas.put(key, new ArrayList<Double>(24));
			for(int i = 0; i < 24; i++){
				dayDatas.get(key).add(0.0);
			}
		}
		for(int i = 0; i<24; i++){
			for(int j = i; j < nIteration; j+=24){
				for(String key : Game.keys){
					if(pricekeys.contains(key))
						dayDatas.get(key).set(i, dayDatas.get(key).get(i)+datas.get(key).get(j)/(10000.0*nSimulation));
					else
						dayDatas.get(key).set(i, dayDatas.get(key).get(i)+datas.get(key).get(j)/(nSimulation));
				}
			}
		}
		int nDay = nIteration/24;
		for(int i = 0; i<24;i++){
			for(String key : Game.keys){
				writer.print(dayDatas.get(key).get(i)/nDay+"\t");
			}
			writer.println(dates.get(i));
		}
		writer.close();
	}

}
