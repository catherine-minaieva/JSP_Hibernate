package service;

import model.Developer;

public interface DeveloperService extends BaseService<Developer> {
    Developer findByName(String name);
}
