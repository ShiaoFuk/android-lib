package com.example.testsoundrecord;

/*
 * 分贝计算
 *
 */
public class DBCalculator {
    /**
     * 根据数组计算平均分贝值
     * @param arr 音频数组
     * @param length 音频数组长度
     * @param sampleBits 采样位数 2的次方
     * @return double 分贝值
     */
    public static double calculate(int[] arr, int length, int sampleBits) {
        if (sampleBits <= 0) {
            return -1.0;
        }
        double sum = 0f;
        for (int i = 0; i < length; ++i) {
            sum += (double) (arr[i] * arr[i]);
        }
        double rms = Math.sqrt(sum / length);
        double range = (double) Math.pow(2, sampleBits - 1);
        double value = rms / range;
        final double reference = 2 * 10e-5;
        return 20 * Math.log10(value / reference);
    }

    /**
     * 根据数组计算平均分贝值
     * @param arr 音频数组
     * @param length 音频数组长度
     * @param sampleBits 采样位数 2的次方
     * @return double 分贝值
     */
    public static double calculate(short[] arr, int length, int sampleBits) {
        if (sampleBits <= 0) {
            return -1.0;
        }
        long sum = 0;
        for (int i = 0; i < length; ++i) {
            sum += arr[i] * arr[i];
        }
        double rms = Math.sqrt(sum / length);
        double range = (double) Math.pow(2, sampleBits - 1);
        double value = rms / range;
        final double reference = 2 * 10e-5;
        return 20 * Math.log10(value / reference);
    }
}
