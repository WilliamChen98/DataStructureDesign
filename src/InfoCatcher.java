import java.io.*;

public class InfoCatcher {
    public int[] num;
    public String[] name;
    public String[] addr;
    public String[] tel;
    public int[] x;
    public int[] y;
    public boolean[] isCapital;

    public InfoCatcher() {
        this.num = new int[20];
        this.name = new String[20];
        this.addr = new String[20];
        this.tel = new String[20];
        this.x = new int[20];
        this.y = new int[20];
        this.isCapital = new boolean[20];
        for (int i = 0; i < 20; i++) {
            this.num[i] = i + 1;
            this.name[i] = "defaultName";
            this.addr[i] = "defaultAddr";
            this.tel[i] = "00000000";
            this.x[i] = -100;
            this.y[i] = -100;
            this.isCapital[i] = false;
        }
    }

    public String getName(int num) {
        return this.name[num];
    }

    public String getAddr(int num) {
        return this.addr[num];
    }

    public String getTel(int num) {
        return this.tel[num];
    }

    public int getX(int num) {
        return this.x[num];
    }

    public int getY(int num) {
        return this.y[num];
    }

    public boolean getIsCapital(int num) {
        return this.isCapital[num];
    }

    public void setName(String name, int num) {
        this.name[num] = name;
    }

    public void setAddr(String addr, int num) {
        this.addr[num] = addr;
    }

    public void setTel(String tel, int num) {
        this.tel[num] = tel;
    }

    public void setX(int x, int num) {
        this.x[num] = x;
    }

    public void setY(int y, int num) {
        this.y[num] = y;
    }

    public void setIsCapital(boolean isCapital, int num) {
        this.isCapital[num] = isCapital;
    }

    public void setAll(String name, String addr, String tel, int x, int y, boolean isCapital, int num) {
        this.name[num] = name;
        this.addr[num] = addr;
        this.tel[num] = tel;
        this.x[num] = x;
        this.y[num] = y;
        this.isCapital[num] = isCapital;
    }

    public void setAll(String readLine, int num) {
        String[] arr = readLine.split("\\s+");
        this.num[num] = Integer.parseInt(arr[0]);
        this.name[num] = arr[1];
        this.addr[num] = arr[2];
        this.tel[num] = arr[3];
        this.x[num] = Integer.parseInt(arr[4]);
        this.y[num] = Integer.parseInt(arr[5]);
        this.isCapital[num] = Boolean.parseBoolean(arr[6]);
    }

    public void readLineFromFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int num = 0;
            String readLine = null;
            while ((readLine = reader.readLine()) != null) {
                setAll(readLine, num);
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public String toString(int num) {
        return this.num[num] + " " + this.name[num] + " " + this.addr[num] + " " + this.tel[num] + " " + this.x[num]
                + " " + this.y[num] + " " + this.isCapital[num];
    }

    public void writeLineToFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            int num = 0;
            while (num < 20) {
                writer.write(this.toString(num));
                writer.newLine();
                num++;
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
