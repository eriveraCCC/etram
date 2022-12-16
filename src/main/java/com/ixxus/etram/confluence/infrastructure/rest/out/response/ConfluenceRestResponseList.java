/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.out.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConfluenceRestResponseList {

    private List<ConfluenceRestResponse> results;

}
