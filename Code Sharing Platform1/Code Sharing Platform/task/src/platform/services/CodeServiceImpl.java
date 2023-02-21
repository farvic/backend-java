package platform.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import platform.entities.Code;
import platform.repositories.CodeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CodeServiceImpl implements CodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeServiceImpl.class);
    private final CodeRepository codeRepository;

    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public Code findById(String id) {
        // ChronoUnit.SECONDS.between(code.getDate(), LocalDateTime.now());
        Code code = codeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The code is no longer available!"));
        LOGGER.info(code.toString());

        if (code.isViewRestricted()) {
            if (code.getViews() == 0) {
                codeRepository.deleteById(id);
                throw new ResourceNotFoundException("The code is no longer available!");
            }
            code.setViews(code.getViews() - 1);
            codeRepository.save(code);
        }
        LOGGER.info(code.toString());

        if (code.isTimeRestricted()) {
            long newTime = code.getTime() - ChronoUnit.SECONDS.between(code.getDate(), LocalDateTime.now());
            if (newTime <= 0) {
                codeRepository.deleteById(id);
                throw new ResourceNotFoundException("The code is no longer available!");
            }
            code.setTime(newTime);
        }

        return code;
    }

    @Override
    public void saveCode(Code code) {
        code.setId(UUID.randomUUID().toString());
        code.setDate(LocalDateTime.now());
        code.setTimeRestricted(code.getTime() != 0);
        code.setViewRestricted(code.getViews() != 0);
        codeRepository.save(code);
    }

    @Override
    public List<Code> getLastTenCodes() {
        return codeRepository.findFirst10ByTimeRestrictedIsFalseAndViewRestrictedIsFalseOrderByDateDesc();
    }
}


