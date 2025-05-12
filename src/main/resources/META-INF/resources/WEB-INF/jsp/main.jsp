<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manufacturer Parts</title>

    <!-- Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Tabulator CSS/JS (CDN) -->
    <link href="https://unpkg.com/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
    <script src="https://unpkg.com/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>

    <!-- jsTree -->
    <script src="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/jstree.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/themes/default/style.min.css" />

</head>
<body class="bg-gray-50 p-6">
<div class="max-w-7xl mx-auto">
   <div class="flex space-x-6">
       <div class="w-2/5 bg-white border border-gray-200 rounded shadow-md p-4 flex flex-col divide-y divide-gray-200">
           <h2 class="text-xl font-semibold mb-4">Product Container 목록</h2>
           <!-- 왼쪽 테이블 영역 -->
           <div id="productContainer-table"></div>
       </div>

        <!-- 오른쪽 프레임 영역 -->
        <div class="w-3/5 bg-white border border-gray-200 rounded shadow-md p-4 flex flex-col divide-y divide-gray-200">
            <!-- 상단 영역 -->
            <div class="pb-4">
                <h2 class="text-xl font-semibold mb-4">Details(속성정보)</h2>
                <div id="detail-top" class="text-sm text-gray-800">
                    <!-- JavaScript로 동적 삽입 -->
                </div>
            </div>

            <!-- 하단 영역 -->
            <div class="pt-4">
                <h3 class="text-lg font-semibold mb-2">Additional Info</h3>
                <div id="detail-bottom" class="text-sm text-gray-800">
                    <div id="folder-table"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="max-w-7xl mx-auto">
        <div id="file-table"></div>
    </div>


</div>


<!-- 예시 모달 -->
<div id="BaseLineReadModal" class="hidden fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center z-50">
    <div class="bg-white p-6 rounded shadow-md w-1/3">
        <h2 class="text-xl font-bold mb-4">베이스라인 조회</h2>
        <p id="modal-content" class="mb-4 text-gray-700">ID: <span id="select-id"></span></p>
        <p class="mb-4 text-gray-700">TODO: BaseLine 조회 API 개발 후 수정 필요(임시 Product 조회 Table 붙여 놓음)</p>
        <div id="BaseLineTable"></div>
        <button id="BaseLineReadModal-close-modal" class="bg-red-500 text-white px-4 py-2 rounded">닫기</button>
    </div>
</div>

<div id="BaseLineAddModal" class="hidden fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center z-50">
    <div class="bg-white p-6 rounded shadow-md w-1/3">
        <h2 class="text-xl font-bold mb-4">베이스라인 추가</h2>
        <p id="modal-content" class="mb-4 text-gray-700">ID: <span id="add-id"></span></p>
        <p class="mb-4 text-gray-700">TODO: BaseLine 등록 API 개발 후 수정 필요</p>
        <button id="BaseLineAddModal-close-modal" class="bg-red-500 text-white px-4 py-2 rounded">닫기</button>
    </div>
</div>

<script language="JavaScript">

    let table;
    let subTable;
    let fileTable;

    let containerId;
    let cabinetId;

    $(document).ready(function () {

        // Tabulator 테이블 정의
        table = new Tabulator("#productContainer-table", {
            height: "500px",
            width: "30%",
            layout: "fitColumns",
            ajaxURL: "/api/productContainer",
            ajaxResponse: function(url, params, response) {
                return response.value;
            },
            columnDefaults:{
                width:150, //set the width on all columns to 200px
            },
            columns: [
                // { title: "ID", field: "ID" },
                { title: "Name", field: "Name"},
                { title: "Organization", field: "OrganizationName" },
                { title: "Created By", field: "CreatedBy" },
            ],
            initialSort: [
                { column: "Name", dir: "asc" }
            ]
        });

        table.on("rowClick", function(e, row){
            const data = row.getData();
            // console.log('call rowClick :', data);

            $("#detail-top").html( `
                    <p><strong>ID:</strong> \${data.ID}</p>
                    <p><strong>Name:</strong> \${data.Name}</p>
                    <p><strong>설명:</strong> \${data.Description}</p>
                    <p><strong>Organization:</strong> \${data.OrganizationName}</p>
                    <p><strong>Created By:</strong> \${data.CreatedBy}</p>
                `);

            if(data.Folders === null){
                return;
            }

            containerId = data.ID;
            cabinetId = data.Folders[0].ID;
            const url = '/api/Containers('+containerId+')/CabinetId('+cabinetId+')';

            subTable = new Tabulator("#folder-table", {
                height: "400px",

                layout: "fitColumns",
                ajaxURL: url,
                ajaxResponse: function(url, params, response) {
                    // console.log('response:', response);
                    return response.Folders;
                },
                columnDefaults:{
                    width:250, //set the width on all columns to 200px
                },
                columns: [
                    // { title: "ID", field: "ID" },
                    { title: "Name", field: "Name"},
                    { title: "Location", field: "Location" },
                    { title: "Description By", field: "Description" },
                ],
                initialSort: [
                    { column: "Name", dir: "asc" }
                ]
            });

            subTable.on("rowClick", function(e, row){
                //TODO: 재귀로 트리 구조 그릴수 있도록 수정해야함
                const data = row.getData();
                // console.log(data);
                const subFolderID = data.ID;
               const url = '/api/Containers('+containerId+')/CabinetId('+cabinetId+')/Folders('+subFolderID+')';

                fileTable = new Tabulator("#file-table", {
                    height: "400px",
                    layout: "fitColumns",
                    width: "100%",
                    ajaxURL: url,
                    ajaxResponse: function(url, params, response) {
                        console.log('response:', response);
                        return response.FolderContents;
                    },
                    columnDefaults:{
                        width:170, //set the width on all columns to 200px
                    },

                    columns: [
                        { title: "베이스라인 조회", width:120,
                            headerSort: false,
                            formatter: function (cell, formatterParams) {
                                let data = cell.getData();
                                if(data['@odata.type'] !== '#PTC.ProdMgmt.Part'){
                                    return;
                                }

                                return `
                                <button class="btn-view" data-id="\${data.ID}">보기</button>
                                `;
                            },
                        },
                        { title: "베이스라인 추가", headerSort: false, width:120,
                            formatter: function (cell, formatterParams) {
                                let data = cell.getData();
                                if(data['@odata.type'] !== '#PTC.ProdMgmt.Part'){
                                    return;
                                }

                                return `
                                <button class="btn-add" data-id="\${data.ID}">추가</button>
                                `;
                            },
                        },
                        { title: "type", formatter: function (cell, formatterParams) {
                                return cell.getData()['@odata.type'];
                            }},
                        { title: "ID", field: "ID" },
                        { title: "Number", field: "Number"},
                        { title: "Name", field: "Name"},
                        { title: "Version", field: "Version"},
                        { title: "FolderLocation", field: "FolderLocation"},
                        { title: "ObjectType", field: "ObjectType"},
                        { title: "CreatedBy", field: "CreatedBy" },

                    ],
                    initialSort: [
                        { column: "Name", dir: "asc" }
                    ]
                });
            });
        });
    });

    // 조회 버튼 클릭 이벤트
    $(document).on('click', '.btn-view', function () {
        const id = $(this).data('id');
        $('#select-id').text(id + ' (조회)');
        $('#BaseLineReadModal').removeClass('hidden');

        const url = "/api/productContainer";
        const baseLineTable = new Tabulator("#BaseLineTable", {
            layout: "fitColumns",
            ajaxURL: url,
            ajaxResponse: function(url, params, response) {
                return response.value;
            },
            columnDefaults:{
                width:150, //set the width on all columns to 200px
            },
            columns: [
                // { title: "ID", field: "ID" },
                { title: "Name", field: "Name"},
                { title: "Organization", field: "OrganizationName" },
                { title: "Created By", field: "CreatedBy" },
            ],
            initialSort: [
                { column: "Name", dir: "asc" }
            ]
        });
    });

    // 추가 버튼 클릭 이벤트
    $(document).on('click', '.btn-add', function () {
        const id = $(this).data('id');
        $('#add-id').text(id + ' (추가)');
        $('#BaseLineAddModal').removeClass('hidden');
    });

    $('#BaseLineReadModal-close-modal').on('click', function () {
        $('#BaseLineReadModal').addClass('hidden');
    });
    $('#BaseLineAddModal-close-modal').on('click', function () {
        $('#BaseLineAddModal').addClass('hidden');
    });

</script>
</body>
</html>