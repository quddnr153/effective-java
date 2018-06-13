package bw.effective.java.rule18;

import java.util.Objects;

/**
 * @author Byungwook lee on 2018. 6. 13.
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public final class WrapperDemo {
    public static void main(String... args) {
        TransformText transformer = new BaseWrapper(new Echo());
        show(transformer.render("blah.")); // 'blah.'

        transformer = new Capitalize(new Echo());
        show(transformer.render("blah.")); // 'BLAH.'

        transformer = new RemovePeriods(new Capitalize(new Echo()));
        show(transformer.render("blah.")); // 'BLAH'

        transformer = new RemovePeriods(new Echo());
        show(transformer.render("blah.")); // 'blah'
    }

    private static void show(String text) {
        System.out.println(text);
    }

    private static final class Echo implements TransformText {
        public String render(String text) {
            return text;
        }
    }

    /**
     * This class both implements the interface AND is constructed
     * with an implementation of the same interface.
     */
    private static class BaseWrapper implements TransformText {
        private TransformText transformText;

        BaseWrapper(TransformText transformText) {
            this.transformText = transformText;
        }

        /**
         * Template method, calls 'before' and 'after' methods.
         */
        public final String render(String text) {
            Objects.requireNonNull(text);

            return after(transformText.render(before(text)));
        }

        /**
         * This default implementation does nothing.
         */
        String before(String text) {
            return text;
        }

        /**
         * This default implementation does nothing.
         */
        String after(String text) {
            return text;
        }

    }

    private static final class Capitalize extends BaseWrapper {
        Capitalize(TransformText transformText) {
            super(transformText);
        }

        @Override
        String before(String text) {
            return text.toUpperCase();
        }
    }

    private static final class RemovePeriods extends BaseWrapper {
        RemovePeriods(TransformText transformText) {
            super(transformText);
        }

        @Override
        String after(String text) {
            return text.replace(".", "");
        }
    }
}
