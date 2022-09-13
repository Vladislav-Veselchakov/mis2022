package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mis2022.config.security.jwt.JwtUtils;
import ru.mis2022.models.entity.Administrator;
import ru.mis2022.models.entity.Invite;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.User;
import ru.mis2022.repositories.AdministratorRepository;
import ru.mis2022.service.entity.AdministratorService;
import ru.mis2022.service.entity.InviteService;
import ru.mis2022.service.entity.MailService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.utils.GenerateRandomString;

import java.time.LocalDateTime;

import static ru.mis2022.utils.DateFormatter.DATE_TIME_FORMATTER;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final PasswordEncoder encoder;
    private final AdministratorRepository administratorRepository;
    private final MailService mailService;
    private final RoleService roleService;
    private final ConfigurableEnvironment env;
    private final InviteService inviteService;
    private final JwtUtils jwtUtils;

    @Override
    public Administrator findByEmail(String email) {
        return administratorRepository.findByEmail(email);
    }

    @Override
    public Administrator findAdministratorById(Long id) {
        return administratorRepository.findAdministratorById(id);
    }

    @Override
    @Transactional
    public Administrator persist(Administrator administrator) {
        administrator.setPassword(encoder.encode(administrator.getPassword()));
        administrator.setRole(roleService.findByName(Role.RolesEnum.ADMIN.name()));
        administrator = administratorRepository.save(administrator);
        String tmpPwd = GenerateRandomString.getRndStr(15);
        String encryptedPwd = encoder.encode(tmpPwd);
        int expPeriod = Integer.valueOf(env.getProperty("mis.property.Invite.expirationPeriod"));
        Invite invite = new Invite(encryptedPwd, LocalDateTime.now().plusHours(expPeriod), administrator.getId());
        inviteService.save(invite);

        String jwt = jwtUtils.generateJwtToken(SecurityContextHolder.getContext().getAuthentication());
        mailService.send("mis2022tmp@mail.ru", "VL mis2222 confirm email n pwd"
            + LocalDateTime.now().format(DATE_TIME_FORMATTER),
    String.format("confirm email and write new password here (in newPassword parameter in url) follow the link:\n\n" +
            "https://%s:%s/api/auth/confirm/emailpassword?Authorization=%s&token=%s&pwd=1",
            env.getProperty("server.address"), System.getenv().get("MAIN_PORT"), jwt, encryptedPwd));


        return administrator;
    }

    @Override
    @Transactional
    public Administrator merge(Administrator administrator) {
        administrator.setPassword(encoder.encode(administrator.getPassword()));
        return administratorRepository.save(administrator);
    }

    @Override
    public boolean isExistById(Long id) {
        return administratorRepository.existsById(id);
    }
}
