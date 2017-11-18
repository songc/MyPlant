package com.songc.core.image;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.AbstractCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.linear.DiagonalMatrix;

import java.util.Collection;

/**
 * Created By @author songc
 * on 2017/11/17
 */
public class DualExponentFit extends AbstractCurveFitter {
    @Override
    protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> collection) {
        final int len = collection.size();
        final double[] target = new double[len];
        final double[] weights = new double[len];
        final double[] initialGUess = {1.0, 1.0, 1.0, 1.0};
        int i = 0;
        for (WeightedObservedPoint point : collection) {
            target[i] = point.getY();
            weights[i] = point.getWeight();
            i += 1;
        }
        final TheoreticalValuesFunction model = new TheoreticalValuesFunction(new TwoExponentFunc(), collection);
        return new LeastSquaresBuilder()
                .maxEvaluations(Integer.MAX_VALUE)
                .maxIterations(Integer.MAX_VALUE)
                .start(initialGUess)
                .target(target)
                .weight(new DiagonalMatrix(weights))
                .model(model.getModelFunction(), model.getModelFunctionJacobian())
                .build();
    }

    /**
     * 2,y = a*exp(b*x) + c*exp(d*x)
     */
    class TwoExponentFunc implements ParametricUnivariateFunction {

        @Override
        public double value(double v, double... params) {
            return params[0] * Math.exp(params[1] * v) + params[2] * Math.exp(params[3] * v);
        }

        @Override
        public double[] gradient(double v, double... params) {
            return new double[]{
                    Math.exp(params[1] * v),
                    params[0] * v * Math.exp(params[1] * v),
                    Math.exp(params[3] * v),
                    params[2] * v * Math.exp(params[3] * v)
            };
        }
    }
}
