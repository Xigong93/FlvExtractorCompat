package pokercc.android.exoplayer.flvextractorcompat;

import android.util.Log;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;

import java.util.List;

final class FlvSeekMap implements SeekMap {

    private final long durationUs;
    private final List<Double> times;
    private final List<Double> filePositions;

    public FlvSeekMap(long durationUs, List<Double> times, List<Double> filePositions) {
        this.durationUs = durationUs;
        this.times = times;
        this.filePositions = filePositions;
    }


    @Override
    public boolean isSeekable() {
        return true;
    }

    @Override
    public long getDurationUs() {
        return durationUs;
    }

    @Override
    public SeekPoints getSeekPoints(long timeUs) {
        return new SeekPoints(new SeekPoint(timeUs, getPosition(timeUs)));
    }

    public long getPosition(long timeUs) {
        final long startT = System.nanoTime();
        double tmpTime = (double) timeUs / 1000 / 1000;
        double tmpAbs = 0;
        int mostCloseIndex = 0;
        //取给定时间最接近的关键帧时间点
        for (int i = 0; i < times.size(); i++) {
            final double abs = Math.abs(times.get(i) - tmpTime);
            if (tmpAbs == 0) {
                tmpAbs = abs;
                mostCloseIndex = i;
            } else if (abs <= tmpAbs) {
                tmpAbs = abs;
                mostCloseIndex = i;
            }
        }
        final double mostCloseTime = times.get(mostCloseIndex);
        //取给定时间最接近的关键帧时间点对应的在文件中的位置
        final double mostClosePos = filePositions.get(mostCloseIndex);
        Log.i("FlvExtractor", "getPosition lost time : " + (System.nanoTime() - startT));
        Log.i("FlvExtractor",
                "mostCloseIndex: " + mostCloseIndex + ",mostCloseTime: " + mostCloseTime + ",mostClosePos: "
                        + mostClosePos);
        Log.i("FlvExtractor", "tmpTime: " + tmpTime);
        Log.i("FlvExtractor", "getPosition: " + timeUs);
        return (long) mostClosePos;

    }
}
