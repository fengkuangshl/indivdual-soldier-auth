# single-soldier-wireless-front

single-soldier-wireless 的前端项目，基于 vue2.x,使用 typescript 来编写，大量用到 extends\interface\泛型等等

## 一、项目运行

### 1、项目的初始化

```
yarn install 或 npm install 或 cnpm install
```

### 2、项目启动

```
yarn dev 或 npm run dev
```

### 3、项目编译

```
yarn build 或 npm run build:dev
```

## 二、组件说明

    组件都是放在src/components目录下
### 1、header
    这主要是页面上头部分组件，展示用户的昵称、头像，是否全屏等等
### 2、left
    这是页面左边的菜单组件，根据用户所拥有的菜单权限，把菜单打印出来 
### 3、page-tabs
    这是满足用户点击菜单时，系统以多页签加载出来。
### 4、table
    这是一个分页组件，主要是对element ui中的table和page组件的组合封装，以简化系统中的分页的代码量，使用也非常方便：
    1、导入分页组件，定义KWTable<T, RT>，注意泛型，T为输出对象，RT为输出对象
        import KWTable from '@/components/table/Table.vue'
        @Ref('kwTableRef')
        readonly kwTableRef!: KWTable<UserSearchRequest, UserInfo>
    2、编写table模板，需要注意的是请在table模板中指定url
        <KWTable url="user/findSysUserByPaged" style="width: 100%" ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <el-table-column prop="userName" sortable="custom" label="帐号"> </el-table-column>
        <el-table-column prop="nickName" sortable="custom" label="昵称"> </el-table-column>
        <el-table-column prop="phone" sortable="custom" label="手机"> </el-table-column>
        <el-table-column
          prop="sex"
          label="性别"
          sortable="custom"
          :formatter="
            row => {
              return row.sex.text
            }
          "
        >
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom">
          <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
        </el-table-column>
        <el-table-column prop="isEnabled" label="状态" sortable="custom">
          <template v-slot="scope">
            <el-switch v-model="scope.row.enabled" active-color="#13ce66" inactive-color="#ff4949" @change="userStatuChanged(scope.row, scope.row.enabled)"> </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <el-tooltip effect="dark" content="重置密码" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini" @click="passwordReset(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </KWTable>
    3、如果有查询条件，请定义分页输入对象，然后调用查询方法进行数据加载
        t: UserSearchRequest = { nickName: '' }
        this.kwTableRef.loadByCondition(this.t)

