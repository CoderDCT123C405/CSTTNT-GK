import java.util.*;

class TrangThai implements Comparable<TrangThai> {
    int[][] banCo;
    int g, h;
    TrangThai cha;
    String huongDi;

    private static final int[] dx = {-1, 0, 1, 0}; 
    private static final int[] dy = {0, 1, 0, -1};
    private static final String[] huong = {"Len", "Phai", "Xuong", "Trai"};
    private static int[][] dich;

    public TrangThai(int[][] banCo, int g, TrangThai cha, String huongDi) {
        this.banCo = banCo;
        this.g = g;
        this.cha = cha;
        this.huongDi = huongDi;
        this.h = tinhHeuristic(banCo);
    }

    public static void setDich(int[][] trangThaiDich) {
        dich = trangThaiDich;
    }

    private int tinhHeuristic(int[][] banCo) {
        int h = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int giaTri = banCo[i][j];
                if (giaTri != 0) {
                    for (int x = 0; x < 3; x++) {
                        for (int y = 0; y < 3; y++) {
                            if (dich[x][y] == giaTri) {
                                h += Math.abs(i - x) + Math.abs(j - y);
                            }
                        }
                    }
                }
            }
        }
        return h;
    }

    @Override
    public int compareTo(TrangThai khac) {
        return Integer.compare(this.g + this.h, khac.g + khac.h);
    }

    private int[] timOTrong() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (banCo[i][j] == 0)
                    return new int[]{i, j};
        return null;
    }

    public List<TrangThai> taoTrangThaiKeTiep(Set<String> daXet) {
        List<TrangThai> keTiep = new ArrayList<>();
        int[] oTrong = timOTrong();
        int zx = oTrong[0], zy = oTrong[1];

        for (int i = 0; i < 4; i++) {
            int nx = zx + dx[i], ny = zy + dy[i];
            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                int[][] banCoMoi = saoChepBanCo(banCo);
                banCoMoi[zx][zy] = banCoMoi[nx][ny];
                banCoMoi[nx][ny] = 0;
                String chuoiTrangThai = Arrays.deepToString(banCoMoi);
                if (!daXet.contains(chuoiTrangThai)) { 
                    keTiep.add(new TrangThai(banCoMoi, g + 1, this, huong[i]));
                }
            }
        }
        return keTiep;
    }

    private int[][] saoChepBanCo(int[][] banCo) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++)
            copy[i] = banCo[i].clone();
        return copy;
    }

    public boolean laDich() {
        return Arrays.deepEquals(this.banCo, dich);
    }

    public String banCoThanhChuoi() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : banCo) {
            for (int num : row)
                sb.append(num).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }
}

public class Giai8So_AKT {
    public static void giai(int[][] trangThaiDau) {
        PriorityQueue<TrangThai> openSet = new PriorityQueue<>();
        Set<String> daXet = new HashSet<>();
        TrangThai batDau = new TrangThai(trangThaiDau, 0, null, "Bat dau");
        openSet.add(batDau);
        daXet.add(Arrays.deepToString(trangThaiDau));

        while (!openSet.isEmpty()) {
            TrangThai hienTai = openSet.poll();

            if (hienTai.laDich()) {
                System.out.println("Tim thay loi giai:");
                inCayTimKiem(hienTai);
                return;
            }

            List<TrangThai> keTiep = hienTai.taoTrangThaiKeTiep(daXet);
            keTiep.sort(Comparator.naturalOrder());

            System.out.println("Trang thai hien tai:");
            System.out.println(hienTai.banCoThanhChuoi());
            System.out.println("---------------------------------------------------");

            System.out.println("Cac trang thai ke tiep:");
            for (TrangThai s : keTiep) {
                System.out.println(s.huongDi + " (g=" + s.g + ", h=" + s.h + ", f=" + (s.g + s.h) + ")");
                System.out.println(s.banCoThanhChuoi());
                openSet.add(s);
                daXet.add(Arrays.deepToString(s.banCo));
            }
        }
    }

    private static void inCayTimKiem(TrangThai dich) {
        List<TrangThai> duongDi = new ArrayList<>();
        while (dich != null) {
            duongDi.add(dich);
            dich = dich.cha;
        }
        Collections.reverse(duongDi);

        System.out.println("So do cay tim kiem:\n");
        for (int i = 0; i < duongDi.size(); i++) {
            TrangThai s = duongDi.get(i);
            System.out.println("Buoc " + i + " - " + s.huongDi + " (g=" + s.g + ", h=" + s.h + ", f=" + (s.g + s.h) + ")");
            System.out.println(s.banCoThanhChuoi());
            if (i < duongDi.size() - 1) {
                System.out.println("---------------------------------------------------");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Nhap trang thai bat dau (dong cach nhau boi dau cach, 3 dong):");
        int[][] trangThaiDau = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                trangThaiDau[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Nhap trang thai dich (dong cach nhau boi dau cach, 3 dong):");
        int[][] trangThaiDich = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                trangThaiDich[i][j] = scanner.nextInt();
            }
        }

        TrangThai.setDich(trangThaiDich);
        giai(trangThaiDau);
    }
}
