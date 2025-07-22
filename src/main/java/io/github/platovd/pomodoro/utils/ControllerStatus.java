package io.github.platovd.pomodoro.utils;

public enum ControllerStatus {
    SESSION {
        @Override
        public String toString() {
            return "Активная сессия";
        }
    },
    PAUSE_SESSION {
        @Override
        public String toString() {
            return "Сессия поставлена на паузу";
        }
    },
    WAIT {
        @Override
        public String toString() {
            return "Солнечные лучи не обжигают, пока не сфокусируются";
        }
    }
}
