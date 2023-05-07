package com.bdd.TP.step;

import com.bdd.TP.dao.DemandaYTemperatura;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class DyTDataWriter implements ItemWriter<DemandaYTemperatura> {

    private static final String INSERT_QUERY = "INSERT INTO demanda_y_temperatura_data(region_id, fecha, demanda, temperatura) VALUES (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public DyTDataWriter(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void write(List<? extends DemandaYTemperatura> items) throws Exception {
        for (DemandaYTemperatura dycData : items) {
            Object[] args = {dycData.getRegionId(), dycData.getFecha(), dycData.getDemanda(), dycData.getTemperatura()};
            jdbcTemplate.update(INSERT_QUERY, args);
        }
    }

}
