package me.tuhin47.paymentservice.controller;

//@SpringBootTest({"server.port=0"})
//@AutoConfigureMockMvc
//@EnableConfigurationProperties
//@ActiveProfiles("test")
public class PaymentControllerTest {

    /*@RegisterExtension
    static WireMockExtension wireMockServer
            = WireMockExtension.newInstance()
            .options(
                    WireMockConfiguration
                            .wireMockConfig()
                            .port(8080)
            )
            .build();

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper
            = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    TokenProvider jwtUtils;

    @Test
    @WithMockUser(username = "User", authorities = { "ROLE_USER" })
    void test_When_doPayment_isSuccess() throws Exception {

        PaymentRequest paymentRequest = getMockPaymentRequest();
        String jwt = getJWTTokenForRoleUser();

        MvcResult mvcResult
                = mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + jwt)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String paymentId = mvcResult.getResponse().getContentAsString();

        Optional<TransactionDetails> transactionDetails = transactionDetailsRepository.findById(Long.valueOf(paymentId));
        assertTrue(transactionDetails.isPresent());

        TransactionDetails p = transactionDetails.get();

        assertEquals(Long.parseLong(paymentId), p.getId());
        assertEquals(paymentRequest.getPaymentMode().name(), p.getPaymentMode());
        assertEquals(paymentRequest.getAmount(), p.getAmount());
        assertEquals(paymentRequest.getOrderId(), p.getOrderId());
    }

    @Test
    @WithMockUser(username = "UserAdmin", authorities = { "ROLE_ADMIN" })
    void test_When_doPayment_WithWrongAccess_thenThrow_403() throws Exception {

        PaymentRequest paymentRequest = getMockPaymentRequest();
        String jwt = getJWTTokenForRoleAdmin();

        MvcResult mvcResult
                = mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + jwt)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();

    }

    @Test
    @WithMockUser(username = "User", authorities = { "ROLE_USER" })
    void test_When_getOrderDetailsByOrderId_isSuccess() throws Exception {

        String jwt = getJWTTokenForRoleUser();

        MvcResult mvcResult
                = mockMvc.perform(MockMvcRequestBuilders.get("/payment/order/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + jwt)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        TransactionDetails transactionDetails = transactionDetailsRepository.findById(1l).get();
        String expectedResponse = getMockPaymentResponse(transactionDetails);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @WithMockUser(username = "User", authorities = { "ROLE_USER" })
    void test_When_getOrderDetailsByOrderId_isNotFound() throws Exception {

        String jwt = getJWTTokenForRoleUser();

        MvcResult mvcResult
                = mockMvc.perform(MockMvcRequestBuilders.get("/payment/order/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + jwt)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }


    private PaymentRequest getMockPaymentRequest() {
        return PaymentRequest.builder()
                .amount(500)
                .orderId(1)
                .paymentMode(PaymentMode.CASH)
                .referenceNumber(null)
                .build();

    }

    private String getMockPaymentResponse(TransactionDetails transactionDetails) throws IOException {

        PaymentResponse paymentResponse
                = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .orderId(transactionDetails.getOrderId())
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .build();
        return objectMapper.writeValueAsString(paymentResponse);
    }

    private String getJWTTokenForRoleUser() throws Exception {

        String response = wireMockServer.stubFor(get("/authenticate/login")
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(copyToString(PaymentControllerTest.class.getClassLoader().getResourceAsStream("mock/GetJWTForUser.json"), defaultCharset())))).getResponse().getBody();


        JWTResponse jwtResponse = objectMapper.readValue(response, JWTResponse.class);

        return jwtUtils.getUserIdFromToken(jwtResponse.getToken());
    }

    private String getJWTTokenForRoleAdmin() throws Exception {

        String response = wireMockServer.stubFor(get("/authenticate/login")
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(copyToString(PaymentControllerTest.class.getClassLoader().getResourceAsStream("mock/GetJWTForAdmin.json"), defaultCharset())))).getResponse().getBody();


        JWTResponse jwtResponse = objectMapper.readValue(response, JWTResponse.class);

        return jwtUtils.getUserIdFromToken(jwtResponse.getToken());
    }
*/
}
