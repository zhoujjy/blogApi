package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zj.Dto.CommentDto;
import com.zj.Dto.UserDto;
import com.zj.Dto.params.CommentParam;
import com.zj.common.R;
import com.zj.entity.Comment;
import com.zj.entity.User;
import com.zj.mapper.CommentMapper;
import com.zj.mapper.UserMapper;
import com.zj.service.CommentService;
import com.zj.service.ThreadService;
import com.zj.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ThreadService threadService;

    /**
     * 写评论
     * @param commentParam
     * @return
     */
    @Override
    public R createComment(CommentParam commentParam) {
        Comment comment = new Comment();
        Integer authorId = ThreadLocalUtil.getUser().getId();
        //设置评论内容
        comment.setContent(commentParam.getContent());
        //设置创建时间
        comment.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //设置文章id
        comment.setArticleId(commentParam.getArticleId());
        //设置作者id
        comment.setAuthorId(authorId);
        //设置级别
        Long parent = commentParam.getParent();
        comment.setLevel(parent==null?1:2);
        //设置父评论
        comment.setParentId(parent == null ? 0 : parent);
        //对谁评论
        Integer toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);

        //异步更新数量
        threadService.updateCommentCount(commentParam.getArticleId());
        return R.success(null);
    }

    /**
     * 查询所有评论
     * @param id
     * @return
     */
    @Override
    public R getComment(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return R.success(this.getChildren(comments));
    }

    private List<CommentDto> getChildren(List<Comment> comments){
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment :comments){
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            //评论作者设置
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userMapper.selectById(comment.getAuthorId()),userDto);
            commentDto.setAuthor(userDto);
            //子评论设置
            if(comment.getLevel() == 1){
                List<CommentDto> commentDtos = findCommentsByParentId(comment.getId());
                commentDto.setChildrens(commentDtos);
            }
            //给谁评论
            if (comment.getLevel() > 1) {
                Integer toUid = comment.getToUid();
                UserDto toUserDto = new UserDto();
                BeanUtils.copyProperties(userMapper.selectById(toUid),toUserDto);
                commentDto.setToUser(toUserDto);
            }
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    private List<CommentDto> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        return getChildren(comments);
    }
}

