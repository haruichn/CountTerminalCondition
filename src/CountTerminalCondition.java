/*
* Title：CountTerminalPosition
* 説明 ：HASCデータに含まれるTerminalPositionとTerminalMountの組み合わせを表示
* @date Created on: 2016/03/25
* @author Author: Haruyuki Ichino
* @version 1.0
*
*/


import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class CountTerminalCondition {

    // TerminalMount&TerminalPositionとそのカウントを補修するハッシュマップ
    static Map<String,Integer> counter = new TreeMap<>();

    public static void main(String[] args) {

        // データの場所指定
        String data_path = "./data/";
        // 軸補正後のデータの格納場所


        // 通常のファイル(隠しファイルでない)のみを取り出すフィルタの作成
        FilenameFilter normalFileFilter = new FilenameFilter() {
            public boolean accept(File file, String name) {
                if (file.isHidden() == false){
                    return true;
                } else {
                    return false;
                }
            }
        };
        // metaファイルのみを取り出すフィルタ
        FilenameFilter metaFileFilter = new FilenameFilter() {
            public boolean accept(File file, String name) {
                if (name.matches(".*meta.*")){
                    return true;
                } else {
                    return false;
                }
            }
        };

        System.out.println("========================================================================");
        System.out.println("1.ファイルの読み込み");
        System.out.println("========================================================================");
        File data_dir = new File(data_path);

        float meta_file_count = 0;
        float good_meta_file_count = 0;

        // data内のファイルを取得
        File[] activity_dirs = data_dir.listFiles(normalFileFilter);

        System.out.println("Activity count = " + activity_dirs.length);

        // 各行動ディレクトリにアクセス
        for (File activity_dir : activity_dirs){
            if(activity_dir.isHidden() == false){
                System.out.println("===================================================");
                System.out.println(activity_dir);
                System.out.println("===================================================");

                // 行動ディレクトリ内のファイルを取得
                File[] person_dirs = activity_dir.listFiles(normalFileFilter);

                System.out.println("person count = " + person_dirs.length);

                // 各personディレクトリにアクセス
                for(File person_dir : person_dirs){
                    if(person_dir.isHidden() == false){
                        System.out.println("======================================");
                        System.out.println(person_dir.getName());
                        System.out.println("======================================");

                        // personディレクトリ内のファイルを取得
                        File[] files = person_dir.listFiles(normalFileFilter);
                        File[] meta_files = person_dir.listFiles(metaFileFilter);

//                        System.out.println("meta files count = " + meta_files.length);
//                        System.out.println();

                        // 各metaファイルにアクセス
                        for(File meta_file : meta_files){
                            meta_file_count++;

                            String meta_file_name = meta_file.getName();
                            System.out.println(meta_file_name);

                            // 名前からID部分の取り出し
                            int idx_hascID = meta_file_name.indexOf(".");
                            String file_id = meta_file_name.substring(0,idx_hascID);



                            // metaファイルのチェック
                            checkTerminalPosition(meta_file);
                        }
                    }
                }
            }
        }

        // 使えるデータの割合の表示
        System.out.println();
        System.out.println("=======================================================================================");
        System.out.println("                                     RESULT");
        System.out.println("=======================================================================================");

        for(Map.Entry<String, Integer> e : counter.entrySet()) {
            String dataRate = String.format("%.2f", e.getValue()/meta_file_count*100);
            System.out.println(String.format("%-70s", e.getKey()) + dataRate + "% (" + String.valueOf(e.getValue()) + "/" + String.valueOf((int)meta_file_count) +")");
        }
    }

    static void checkTerminalPosition(File file){

        String mount = "";
        String position = "";

        try {
            //ファイルを読み込む
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //読み込んだファイルを１行ずつ処理する
            String line_str;

            for(int i=0; (line_str = br.readLine()) != null; i++){

                // TerminalMountの処理
                if (line_str.indexOf("TerminalMount") != -1) {


                    // TerminalMountから内容の取り出し
                    int idx_mount = line_str.indexOf(":") + 1;
                    mount = line_str.substring(idx_mount, line_str.length());
                    mount = mount.trim(); // 空白の削除

                    System.out.println("\t"+mount);

                }

                // TerminalPositionの処理
                else if (line_str.indexOf("TerminalPosition") != -1) {

                    // TerminalPositionから内容の取り出し
                    int idx_position = line_str.indexOf(":") + 1;
                    position = line_str.substring(idx_position, line_str.length());
                    position = position.trim(); // 空白の削除

                    System.out.println("\t"+position);

                }
            }

            //終了処理
            br.close();

        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
        }

        String TerminalCondition = "TerminalMount:"+mount+"\tTerminalPosition:"+position;

        if (!counter.containsKey(TerminalCondition)){
            counter.put(TerminalCondition, 1);
        } else {
            int count = counter.get(TerminalCondition);
            counter.put(TerminalCondition, count+1);
        }

    }

}
