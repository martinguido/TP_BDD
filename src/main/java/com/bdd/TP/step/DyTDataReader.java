package com.bdd.TP.step;
/*
import com.bdd.TP.dao.DemandaYTemperatura;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;

public class DyTDataReader implements ItemReader<DemandaYTemperatura> {

    private final String csvFilePath;
    private BufferedReader reader;

    public DyTDataReader(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public DemandaYTemperatura read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (reader == null) {
            reader = new BufferedReader(new FileReader(csvFilePath));
        }

        String line = reader.readLine();
        if (line == null) {
            reader.close();
            return null;
        }

        String[] fields = line.split(", ");

        DemandaYTemperatura DyTData = new DemandaYTemperatura();
        DyTData.setRegionId(Integer.parseInt(fields[0]));
        DyTData.setFecha(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(fields[1]));
        DyTData.setDemanda(Integer.parseInt(fields[2]));
        DyTData.setTemperatura(Double.parseDouble(fields[3]));

        return DyTData;
    }

}*/
