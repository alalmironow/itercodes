package ru.mironow.auth;

import org.springframework.stereotype.Component;
import ru.mironow.data.DataFile;
import ru.mironow.datasources.DataFromFileSource;

import java.io.IOException;

/**
 * Реализация сервиса авторизации
 *
 * Created By Root to date 19.01.2019
 */
@Component
public class AuthServiceImpl implements AuthService {

    private final DataFromFileSource dataFromFileSource;

    public AuthServiceImpl(DataFromFileSource dataFromFileSource) throws IOException {
        this.dataFromFileSource = dataFromFileSource;
    }

    @Override
    public User register(RegisterEntity registerEntity) throws UserExistException, IOException {
        DataFile data = dataFromFileSource.getData();
        System.out.println(data);
        if(data.getData().containsKey(registerEntity.getLogin())) {
            throw new UserExistException();
        }
        User user = new User();
        user.setLogin(registerEntity.getLogin());
        user.setFullname(registerEntity.getFullName());
        user.setPassword(registerEntity.getPassword());
        data.getData().put(registerEntity.getLogin(), user);
        dataFromFileSource.saveData(data);
        return user;
    }

    @Override
    public User login(LoginEntity entity) throws UserNotExistException {
        DataFile data = dataFromFileSource.getData();
        if(!data.getData().containsKey(entity.getLogin()) || !data.getData().get(entity.getLogin()).getPassword().equals(entity.getPassword())) {
            throw new UserNotExistException();
        }
        return data.getData().get(entity.getLogin());
    }
}