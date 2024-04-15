package kyonggiyo.adapter.out.persistence.image;

import kyonggiyo.domain.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageBatchRepositoryImpl implements ImageBatchRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void saveAllInBatch(List<Image> images) {
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[images.size()];
        int idx = 0;

        for (Image image : images) {
            SqlParameterSource source = new MapSqlParameterSource()
                    .addValue("upload_key", image.getKey())
                    .addValue("image_type", image.getImageType().name())
                    .addValue("reference_id", image.getReferenceId())
                    .addValue("created_at", image.getCreatedAt())
                    .addValue("updated_at", image.getUpdatedAt());
            sqlParameterSources[idx++] = source;
        }
        String sql = """
                insert into images (upload_key, image_type, reference_id, created_at, updated_at)
                values(:upload_key, :image_type, :reference_id, :created_at, :updated_at)""";
        jdbcTemplate.batchUpdate(sql, sqlParameterSources);
    }

}
