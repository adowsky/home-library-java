package com.adowsky.service;

import com.adowsky.model.User;
import com.adowsky.service.entities.PermissionEntity;
import com.adowsky.service.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public List<String> findLibraryOwnerUsernamesGranted(long userId) {
        UserEntity userEntity = userRepository.findOne(userId);

        List<PermissionEntity> permissionEntities = permissionRepository.findAllByGrantedTo(userEntity);
        return permissionEntities.stream()
                .map(permission -> permission.getOwner().getUsername())
                .collect(Collectors.toList());
    }

    void createPermissionForNewUser(UserEntity userEntity) {
        permissionRepository.save(new PermissionEntity(null, userEntity, userEntity));
    }
}
