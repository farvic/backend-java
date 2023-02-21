package platform.services;

import platform.entities.Code;

import java.util.List;

public interface CodeService {

    Code findById(String id);

    void saveCode(Code code);

    List<Code> getLastTenCodes();
}
