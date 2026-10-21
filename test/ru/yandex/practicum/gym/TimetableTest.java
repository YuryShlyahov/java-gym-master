package ru.yandex.practicum.gym;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {
    private Coach coach;
    private Coach secondCoach;
    private Coach thirdCoach;
    private Timetable timetable;
    private Group groupChild;
    private Group groupAdult;
    private TrainingSession singleTrainingSession;

    @BeforeEach
    public void setUp() {
        timetable = new Timetable();
        coach = new Coach("Васильев", "Николай", "Сергеевич");
        secondCoach = new Coach("Ходаков", "Александр", "Иванович");
        thirdCoach = new Coach("Березин", "Геннадий", "Валерьевич");
        groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        singleTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
    }

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).isEmpty(), "Ошибка, мапа тренировок за день не пуста до добавления тренировок.");
        timetable.addNewTrainingSession(singleTrainingSession);

        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).get(singleTrainingSession.getTimeOfDay()).contains(singleTrainingSession), "Ошибка, при добавлении тренировки, она не попадает в мапу по дню.");
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), "Ошибка, размер мапы тренировок изменяется некорректно при добавлении тренировки.");
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty(), "Ошибка, мапа тренировок за день не пуста, хотя тренировка добавлена на другой день.");
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), "Ошибка, размер мапы тренировок изменяется некорректно при добавлении одной тренировки.");
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size(), "Ошибка, размер мапы тренировок изменяется некорректно при добавлении нескольких тренировок.");
        List<TimeOfDay> actual = new ArrayList<>(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).keySet());
        List<TimeOfDay> expected = new ArrayList<>(List.of(thursdayChildTrainingSession.getTimeOfDay(), thursdayAdultTrainingSession.getTimeOfDay()));
        assertEquals(actual, expected, "Ошибка, тренировки неверно сортируются по времени.");
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty(), "Ошибка, мапа тренировок дня не пуста, хотя тренировки в него не добавлялись.");
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).isEmpty(), "Ошибка, список тренировок не пуст до добавления тренировок.");
        timetable.addNewTrainingSession(singleTrainingSession);
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size(), "Ошибка, список по времени не изменяется при добавлении тренировки");
        assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).contains(singleTrainingSession), "Ошибка, список по времени не содержит добавленной тренировки.");
        assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).isEmpty(), "Ошибка, список по времени не пуст, хотя добавление шло на другое время.");
    }

    @Test
    public void testGetCountByCoaches() {


        TrainingSession firstTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(groupAdult, secondCoach, DayOfWeek.WEDNESDAY, new TimeOfDay(19, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(groupChild, thirdCoach, DayOfWeek.SATURDAY, new TimeOfDay(7, 0));
        TrainingSession fourthTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.TUESDAY, new TimeOfDay(18, 0));
        TrainingSession fifthTrainingSession = new TrainingSession(groupChild, secondCoach, DayOfWeek.SUNDAY, new TimeOfDay(12, 0));
        TrainingSession sixthTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.FRIDAY, new TimeOfDay(15, 0));
        assertTrue(timetable.getCountByCoaches().isEmpty(), "Ошибка, список подсчета нагрузки тренеров не пуст до добавления тренировок.");
        timetable.addNewTrainingSession(firstTrainingSession);
        assertEquals(coach, timetable.getCountByCoaches().getFirst().getCoach(), "Ошибка, при добавлении одной тренировки, ее тренер не добавляется в список на подсчет нагрузки тренеров.");
        assertEquals(1, timetable.getCountByCoaches().size(), "Ошибка, неверно изменяется размер списка нагрузки тренеров при добавлении одной тренировки.");
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);
        timetable.addNewTrainingSession(fourthTrainingSession);
        timetable.addNewTrainingSession(fifthTrainingSession);
        timetable.addNewTrainingSession(sixthTrainingSession);

        List<CounterOfTrainings> expected = new ArrayList<>(List.of(new CounterOfTrainings(coach, 3), new CounterOfTrainings(secondCoach, 2), new CounterOfTrainings(thirdCoach, 1)));
        assertEquals(expected, timetable.getCountByCoaches(), "Ошибка, неверно идет подсчет нагрузки тренеров и их сортировка в порядке по убыванию.");
    }

    @Test
    public void testGetCountByCoachesSameCount() {
        TrainingSession firstTrainingSession = new TrainingSession(groupChild, secondCoach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(19, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(groupChild, thirdCoach, DayOfWeek.SATURDAY, new TimeOfDay(7, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        List<CounterOfTrainings> expected = new ArrayList<>(List.of(new CounterOfTrainings(thirdCoach, 1), new CounterOfTrainings(coach, 1), new CounterOfTrainings(secondCoach, 1)));

        assertEquals(expected, timetable.getCountByCoaches(), "Ошибка, неверно идет сортировка результатов подсчета нагрузки тренеров при равной нагрузке.");
    }

    @Test
    public void testGetCountByCoachesSameDayAndTime(){
        TrainingSession firstTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession secondTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);
        List<CounterOfTrainings> expected = new ArrayList<>(List.of(new CounterOfTrainings(coach, 3)));
        assertEquals(expected, timetable.getCountByCoaches(), "Ошибка, неверно идет подсчет нагрузки при тренировках в один день и одно время.");
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        TrainingSession firstTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession secondTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);
        List<TrainingSession> result = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        assertTrue(result.contains(firstTrainingSession), "Ошибка, не добавлена тренировка № 1 при добавлении тренировок в одно время");
        assertTrue(result.contains(secondTrainingSession), "Ошибка, не добавлена тренировка № 2 при добавлении тренировок в одно время");
        assertTrue(result.contains(thirdTrainingSession), "Ошибка, не добавлена тренировка № 3 при добавлении тренировок в одно время");
        assertEquals(3, result.size(), "Ошибка, неверный размер списка тренировок при нескольких тренировках в одно время.");
    }
}
