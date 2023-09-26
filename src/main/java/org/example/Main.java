package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



public class Main {

    /*
    Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке,
    разделенные пробелом: Фамилия Имя Отчество датарождения номертелефона пол
    Форматы данных:
    фамилия, имя, отчество - строки
    дата рождения - строка формата dd.mm.yyyy
    номер телефона - целое беззнаковое число без форматирования
    пол - символ латиницей f или m.

    Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым,
    вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных,
    чем требуется. Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
    Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
    Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано,
    пользователю выведено сообщение с информацией, что именно неверно.
    Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии,
    в него в одну строку должны записаться полученные данные, вида
    <Фамилия><Имя><Отчество><датарождения><номертелефона><пол>

    Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
    Не забудьте закрыть соединение с файлом.
    При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
    Данная промежуточная аттестация оценивается по системе "зачет" / "не зачет"
    "Зачет" ставится, если слушатель успешно выполнил "Незачет"" ставится, если слушатель успешно выполнил
    Критерии оценивания:
    Слушатель напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробело
    */
    public static void main(String[] args) throws Exception{
        try{
            inputData();
        }
        catch (Exception e){
            System.out.printf("Ошибка при выполнении ввода.", e.fillInStackTrace());
        }
    }

    public static void inputData() throws Exception{

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные через пробел: ");

        String surname = null;
        String name = null;
        String patronymic = null;
        Date birthDate = null;
        Integer phoneNumber = null;
        char sex = 0;

        String inputData = scanner.next();

        String[] arrayData = inputData.split(" ");
        if (arrayData.length < 6){
            throw new Exception("Введено неверное количество параметров.");
        }
        for (int i = 0; i < arrayData.length; i++){
            if (arrayData[i] == null){
                throw new Exception("Данные введены с ошибкой.");
            }
        }

        try {
            surname = arrayData[0].substring(0,1).toUpperCase() + arrayData[0].substring(1).toLowerCase();
            name = arrayData[1].substring(0,1).toUpperCase() + arrayData[1].substring(1).toLowerCase();
            patronymic = arrayData[2].substring(0,1).toUpperCase() + arrayData[2].substring(1).toLowerCase();
            if (surname.length() == 0 | name.length() == 0 | patronymic.length() == 0){
                throw new Exception("Введно неверное количество символов для ФИО.");
            }
        }
        catch (IndexOutOfBoundsException e){
            System.out.printf("Введно недопустимое количество символов.", e.getMessage());
        }

        try{
            if(arrayData[3].length() != 6){
                throw new Exception("Невернео число значений параметров даты.");
            }
            birthDate = new SimpleDateFormat("dd.MM.yyyy").parse(arrayData[3]);
        }
        catch (IllegalArgumentException e){
            System.out.printf("Неверный формат данных в параметре даты.", e.getMessage());
        }


        try{
            phoneNumber = Integer.parseInt(arrayData[4]);
        }
        catch (NumberFormatException e){
            System.out.printf("Неверный формат данных при вводе телефона.", e.getMessage());
        }

        try{
            sex = arrayData[5].toLowerCase().charAt(0);
            if (sex != 'm' && sex != 'f'){
                throw new Exception("Недопустимое значение для параматера пол.");
            }
        }
        catch (IndexOutOfBoundsException e){
            System.out.printf("Введно недопустимое количество символов.", e.getMessage());
        }

        if (surname == null) {
            throw new Exception("Недопустимое количество символов.");
        }
        String fileName = "personData\\" + surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write("<" + surname + "><" + name + "><" + patronymic + "><" + birthDate +
                    "><" + phoneNumber + "><" + sex + ">");
        }
        catch (IOException e){
            System.out.printf("Ошибка при работе с файлом", e.getMessage());
        }
    }
}