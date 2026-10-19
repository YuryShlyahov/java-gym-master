package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable = new HashMap<>();
        DayOfWeek trainingDay = trainingSession.getDayOfWeek();
        Map<TimeOfDay, List<TrainingSession>> trainingSessionMap = new TreeMap<>();
        List<TrainingSession> trainingSessionList = new ArrayList<>();
        trainingSessionList.add(trainingSession);
        if (!timetable.containsKey(trainingDay)) {
            trainingSessionMap.put(trainingSession.getTimeOfDay(), trainingSessionList);
            timetable.put(trainingDay, trainingSessionMap);
        } else {
            TimeOfDay trainingTime = trainingSession.getTimeOfDay();
            trainingSessionMap = timetable.get(trainingDay);
            if (!trainingSessionMap.containsKey(trainingTime)) {
                trainingSessionMap.get(trainingTime).add(trainingSession);
            } else {
                trainingSessionMap.put(trainingTime, trainingSessionList);
            }
        }
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> trainingSessionMap = timetable.get(dayOfWeek);
        return trainingSessionMap.get(timeOfDay);
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }
}
