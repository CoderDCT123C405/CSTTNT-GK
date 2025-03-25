import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

class Segment {
    int start, end;

    Segment(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must be <= end");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }
}

public class NonOverlappingSegments extends JPanel {
    private final List<Segment> segments;

    public NonOverlappingSegments(List<Segment> segments) {
        this.segments = segments;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int y = 50;

        int scaleFactor = Math.max(1, getWidth() / 100); // Điều chỉnh hệ số nhân

        for (Segment s : segments) {
            int xStart = s.start * scaleFactor;
            int xEnd = s.end * scaleFactor;
            g.drawLine(xStart, y, xEnd, y);
            g.fillOval(xStart - 3, y - 3, 6, 6);
            g.fillOval(xEnd - 3, y - 3, 6, 6);
            g.drawString(s.toString(), xStart, y - 5);
            y += 20;
        }
    }

    public static void main(String[] args) {
        List<Segment> segments = Arrays.asList(
            new Segment(1, 8),
            new Segment(5, 19),
            new Segment(19, 62),
            new Segment(6, 90)
        );

        System.out.println("Chon theo bat dau som nhat:");
        List<Segment> startFirst = selectSegments(new ArrayList<>(segments), Comparator.comparingInt(s -> s.start));
        System.out.println(startFirst);

        System.out.println("\nChon theo do dai ngan nhat:");
        List<Segment> shortestFirst = selectSegments(new ArrayList<>(segments), Comparator.comparingInt(s -> s.end - s.start));
        System.out.println(shortestFirst);

        System.out.println("\nChon theo ket thuc som nhat:");
        List<Segment> endFirst = selectSegments(new ArrayList<>(segments), Comparator.comparingInt(s -> s.end));
        System.out.println(endFirst);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Non-Overlapping Segments");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 300); // Điều chỉnh kích thước cửa sổ để tránh tràn
            frame.add(new NonOverlappingSegments(segments));
            frame.setVisible(true);
        });
    }

    static List<Segment> selectSegments(List<Segment> segments, Comparator<Segment> comparator) {
        segments.sort(comparator);
        List<Segment> result = new ArrayList<>();
        int lastEnd = -1;

        for (Segment s : segments) {
            if (s.start >= lastEnd) {
                result.add(s);
                lastEnd = s.end;
            }
        }

        return result;
    }
}
