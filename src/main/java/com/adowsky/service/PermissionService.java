package com.adowsky.service;

import com.adowsky.service.entities.PermissionEntity;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.UserException;
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

    public boolean hasAccessTo(long userId, String ownerUsername) {
        UserEntity userEntity = userRepository.getByUsername(ownerUsername)
                .orElseThrow(UserException::noSuchUser);

        return permissionRepository.findFirstByOwnerAndGrantedTo(userEntity, new UserEntity(userId))
        .map(o -> true).orElse(false);
    }

    void createPermissionForNewUser(UserEntity userEntity) {
        permissionRepository.save(new PermissionEntity(null, userEntity, userEntity));
    }

    void grantPermissionToUser(UserEntity grantedTo, UserEntity owner){
        permissionRepository.save(new PermissionEntity(null, owner, grantedTo));
    }

}
