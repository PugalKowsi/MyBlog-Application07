package com.myblog7.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> PostDto;
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private long totalElements;
    private boolean last;
}
