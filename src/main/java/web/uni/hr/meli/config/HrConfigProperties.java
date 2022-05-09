package web.uni.hr.meli.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "hr")
@Component
public class HrConfigProperties {

    private Raise raise = new Raise();

    public Raise getRaise() {
        return raise;
    }

    public void setRaise(Raise raise) {
        this.raise = raise;
    }

    public static class Raise {
        private Default def = new Default();
        private Smart smart = new Smart();

        public Default getDef() {
            return def;
        }

        public void setDef(Default def) {
            this.def = def;
        }

        public Smart getSmart() {
            return smart;
        }

        public void setSmart(Smart smart) {
            this.smart = smart;
        }
    }

    public static class Default {
        private int percent;

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }

    public static class Smart {
        private int highestPercent;
        private int lowestPercent;
        private int moderatePercent;

        private double lowestLimit;
        private int middleLimit;
        private int highestLimit;

        public int getHighestPercent() {
            return highestPercent;
        }

        public void setHighestPercent(int highestPercent) {
            this.highestPercent = highestPercent;
        }

        public int getLowestPercent() {
            return lowestPercent;
        }

        public void setLowestPercent(int lowestPercent) {
            this.lowestPercent = lowestPercent;
        }

        public int getModeratePercent() {
            return moderatePercent;
        }

        public void setModeratePercent(int moderatePercent) {
            this.moderatePercent = moderatePercent;
        }

        public double getLowestLimit() {
            return lowestLimit;
        }

        public void setLowestLimit(double lowestLimit) {
            this.lowestLimit = lowestLimit;
        }

        public int getMiddleLimit() {
            return middleLimit;
        }

        public void setMiddleLimit(int middleLimit) {
            this.middleLimit = middleLimit;
        }

        public int getHighestLimit() {
            return highestLimit;
        }

        public void setHighestLimit(int highestLimit) {
            this.highestLimit = highestLimit;
        }
    }
}
