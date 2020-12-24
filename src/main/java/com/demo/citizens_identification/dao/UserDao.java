package com.demo.citizens_identification.dao;

import com.demo.citizens_identification.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserDao implements Dao<UserEntity> {

    //TODO need to be implemented

    @Override
    public Optional<UserEntity> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> getByHash(String hash) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> save(UserEntity userEntity) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> update(UserEntity userEntity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteByHash(String hash) {

    }

    public Mono<ServerResponse> deployContract(final ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(APIRequest.class)
                .map(this::getTransactionManager)
                .map(this::deployContract)
                .flatMap(this::generateResponse);
    }

    private TransactionManager getTransactionManager(final APIRequest apiRequest) {
        log.info("[HANDLER] privateFor = {}", apiRequest.getPrivateFor());
        TransactionManager txManager;
        if (isPrivate(apiRequest.getPrivateFor())) {
            if (apiRequest.getPrivateFor().size() == 0) {
                apiRequest.getPrivateFor().add(this.blockchainConfig.getTesseraPublicKey());
            }
            txManager = new TesseraTransactionManager(this.quorum, this.credentials, this.blockchainConfig.getTesseraPublicKey(), apiRequest.getPrivateFor(), this.tessera);
        } else {
            txManager = new GethTransactionManager(this.web3j, this.credentials);
        }

        return txManager;
    }

    private boolean isPrivate(final List<String> limitedTo) {
        return limitedTo == null || limitedTo.size() == 0 || !limitedTo.get(0).equals("public");
    }

    private APIResponse deployContract(final TransactionManager txManager) {
        log.info("[HANDLER] deploying new smart-contract");
        final String data = QuorumDemo.getBinary();
        final TransactionReceipt txReceipt = txManager.executeTransaction(GAS_PRICE, DEPLOY_GAS_LIMIT, null, data);
        final APIResponse apiResponse = APIResponse.newInstance(txReceipt);
        this.deployedContract = txReceipt.getContractAddress();
        log.info("[HANDLER] contract has been successfully deployed. Result: {}", apiResponse.getMap());

        return apiResponse;
    }

    private Mono<ServerResponse> generateResponse(final APIResponse apiResponse) {
        return ServerResponse
                .ok()
                .body(Mono.just(apiResponse.getMap()), Map.class);
    }

    /**
     * Send transaction on update user in smart-contract.
     *
     * @param serverRequest
     *          - {@link ServerRequest} object with request information
     * @return {@link ServerResponse} object with response data
     */
    public Mono<ServerResponse> updateUser(final ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(APIRequest.class)
                .map(this::sendTransaction)
                .flatMap(this::generateResponse);
    }

    private APIResponse sendTransaction(final APIRequest apiRequest) {
        final TransactionManager txManager = getTransactionManager(apiRequest);
        log.info("[HANDLER] sending new transaction");
        final String data = QuorumDemo.getDataOnWriteUser(apiRequest.getUser());
        final TransactionReceipt txReceipt = txManager.executeTransaction(GAS_PRICE, TX_GAS_LIMIT, this.deployedContract, data);
        final APIResponse apiResponse = APIResponse.newInstance(txReceipt);
        log.info("[HANDLER] transaction has been successfully executed. Result: {}", apiResponse.getMap());

        return apiResponse;
    }

    /**
     * Read user from smart-contract.
     *
     * @param serverRequest
     *          - {@link ServerRequest} object with request information
     * @return {@link ServerResponse} object with response data
     */
    public Mono<ServerResponse> getUser(final ServerRequest serverRequest) {
        final APIResponse apiResponse = getUser();
        return generateResponse(apiResponse);
    }

    private APIResponse getUser() {
        log.info("[HANDLER] reading user from smart-contract");
        final QuorumDemo quorumDemo = QuorumDemo.load(this.deployedContract, this.web3j, this.credentials, new StaticGasProvider(GAS_PRICE, DEPLOY_GAS_LIMIT));
        final String user = readUserFromSmartContract(quorumDemo);
        final APIResponse apiResponse = APIResponse.newInstance(user);
        log.info("[HANDLER] user: '{}'", user);

        return apiResponse;
    }

    private String readUserFromSmartContract(final QuorumDemo quorumDemo) {
        try {
            return quorumDemo.user().send().getValue();
        } catch (Exception ex) {
            log.info("[HANDLER] exception while reading user from smart-contract: {}", ex);
            return null;
        }
    }
}
